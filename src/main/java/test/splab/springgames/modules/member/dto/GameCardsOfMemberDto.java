package test.splab.springgames.modules.member.dto;

import lombok.Builder;
import lombok.Getter;
import test.splab.springgames.modules.GameCard;

@Getter
@Builder
public class GameCardsOfMemberDto {

    Long id;
    String game;
    String title;
    Long serialNumber;
    double price;

    public static GameCardsOfMemberDto from(GameCard gameCard) {
        return GameCardsOfMemberDto.builder()
                .id(gameCard.getGameCardId())
                .game(gameCard.getGame().getName())
                .title(gameCard.getTitle())
                .serialNumber(gameCard.getSerialNumber())
                .price(gameCard.getPrice())
                .build();
    }
}
