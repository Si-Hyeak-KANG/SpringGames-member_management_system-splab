package test.splab.springgames.modules.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.splab.springgames.modules.game.Game;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findGameByName(String name);
}
