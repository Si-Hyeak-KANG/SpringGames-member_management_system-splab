package test.splab.springgames.modules.game.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.splab.springgames.modules.game.dto.GameNameResultDto;
import test.splab.springgames.modules.game.repository.GameRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Override
    public List<GameNameResultDto> getGameNameList() {
        return gameRepository.findAll().stream()
                .map(GameNameResultDto::from)
                .collect(Collectors.toList());
    }
}
