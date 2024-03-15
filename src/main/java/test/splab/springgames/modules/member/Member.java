package test.splab.springgames.modules.member;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import test.splab.springgames.modules.GameCard;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDateTime joinAt;

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

    public static Member of(String name, String email, LocalDateTime joinAt) {
        return new Member(name, email, joinAt);
    }

    private Member(String name, String email, LocalDateTime joinAt) {
        this.name = name;
        this.email = email;
        this.joinAt = joinAt;
        level = Level.BRONZE;
        cardTotalCount = 0;
        cardTotalPrice = 0;
    }
}
