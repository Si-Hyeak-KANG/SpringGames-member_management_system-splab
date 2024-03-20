package test.splab.springgames.modules.game.service;

import test.splab.springgames.modules.game.dto.GameNameResultDto;

import java.util.List;

public interface GameService {

    /**
     * 시스템에 등록된 게임 이름 List 를 가져옵니다.
     * @return List - Game
     */
    List<GameNameResultDto> getGameNameList();
}
