package test.splab.springgames.modules.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import test.splab.springgames.modules.card.GameCard;
import test.splab.springgames.modules.member.dto.EditFormDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NamedEntityGraph(
        name = "Member.withGameCardList",
        attributeNodes = @NamedAttributeNode(value = "gameCardList", subgraph = "gameCardListSubgraph"),
        subgraphs = @NamedSubgraph(name = "gameCardListSubgraph", attributeNodes = @NamedAttributeNode("game")))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
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
}
