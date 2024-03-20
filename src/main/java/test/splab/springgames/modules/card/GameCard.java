package test.splab.springgames.modules.card;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import test.splab.springgames.modules.game.Game;
import test.splab.springgames.modules.member.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class GameCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameCardId;

    private String title;

    private Long serialNumber;

    private double price;
    
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private GameCard(String title, Long serialNumber, double price) {
        this.title = title;
        this.serialNumber = serialNumber;
        this.price = price;
    }

    public static GameCard of(String title, Long serialNumber, double price) {
        return new GameCard(title, serialNumber, price);
    }
    public void addMember(Member member) {
        this.member=member;
        member.getGameCardList().add(this);
    }

    public void addGame(Game game) {
        this.game = game;
    }

    public void addMemberIfNotExists(Member member) {
        if(this.member==null) {
            addMember(member);
        }
    }

}
