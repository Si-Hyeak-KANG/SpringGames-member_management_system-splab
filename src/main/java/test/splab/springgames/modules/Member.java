package test.splab.springgames.modules;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
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
}
