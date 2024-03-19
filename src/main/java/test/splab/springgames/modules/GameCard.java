package test.splab.springgames.modules;

import jakarta.persistence.*;
import lombok.Getter;
import test.splab.springgames.modules.member.Member;

@Getter
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

    public void addMember(Member member) {
        this.member=member;

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
