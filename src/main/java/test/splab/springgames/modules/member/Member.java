package test.splab.springgames.modules.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import test.splab.springgames.modules.card.GameCard;
import test.splab.springgames.modules.member.dto.EditFormDto;
import test.splab.springgames.modules.member.level.Level;
import test.splab.springgames.modules.member.level.LevelPolicy;

import java.time.LocalDate;
import java.util.*;

@NamedEntityGraph(
        name = "Member.withGameCardList",
        attributeNodes = @NamedAttributeNode(value = "gameCardList", subgraph = "gameCardListSubgraph"),
        subgraphs = @NamedSubgraph(name = "gameCardListSubgraph", attributeNodes = @NamedAttributeNode("game")))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(indexes = {
        @Index(columnList = "level"),
        @Index(columnList = "name"),
        @Index(columnList = "level, name")
})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(nullable = false)
    private LocalDate joinAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Level level;

    @Column(nullable = false)
    private int cardTotalCount;

    @Column(nullable = false)
    private double cardTotalPrice;

    @OneToMany(mappedBy = "member")
    private List<GameCard> gameCardList = new ArrayList<>();

    public void addGameCard(GameCard gameCard) {
        this.gameCardList.add(gameCard);
        gameCard.addMemberIfNotExists(this);
    }

    public static Member of(String name, String email, LocalDate joinAt) {
        return new Member(name, email, joinAt);
    }

    private Member(String name, String email, LocalDate joinAt) {
        this.name = name;
        this.email = email;
        this.joinAt = joinAt;
        level = Level.BRONZE;
        cardTotalCount = 0;
        cardTotalPrice = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member member)) return false;
        return Objects.equals(memberId, member.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }

    public void updateInfo(EditFormDto editFormDto) {
        this.name=editFormDto.getName();
        this.email=editFormDto.getEmail();
        this.joinAt=editFormDto.getJoinAtToLocalDate();
    }

    public void updateCardTotalCountAndPrice() {
        this.cardTotalCount = gameCardList.size();
        double totalPrice = gameCardList.stream().mapToDouble(GameCard::getPrice).sum();
        this.cardTotalPrice = roundToThirdDecimal(totalPrice);
    }

    private double roundToThirdDecimal(double totalPrice) {
        return (double) Math.round(totalPrice * 100) / 100;
    }

    /**
     * 1) BASIC
     * 2) SILVER - 1장 이상 유효카드  
     * 3) GOLD 
     * - 유효카드 4장 이상이거나 2-3장의 합계가 100달러 이상
     * - 소유한 카드의 게임이 2종류 이상 존재
     */
    public boolean isChangeLevelAccordingToPolicy() {
        Level baseLevel = this.level;
        Level updateLevel = Level.BRONZE;
        if(LevelPolicy.isSilver(this.gameCardList)) updateLevel = Level.SILVER;
        if (LevelPolicy.isGold(this.gameCardList)) updateLevel = Level.GOLD;
        this.level = updateLevel;
        return !baseLevel.equals(this.level);
    }
}
