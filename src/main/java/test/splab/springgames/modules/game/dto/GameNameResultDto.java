package test.splab.springgames.modules.game.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import test.splab.springgames.modules.game.Game;

@Getter
@Setter
@AllArgsConstructor
public class GameNameResultDto {

    String name;

    public static GameNameResultDto from(Game game) {
        return new GameNameResultDto(game.getName());
    }
}
