package test.splab.springgames.modules.member.level;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import test.splab.springgames.modules.card.GameCard;
import test.splab.springgames.modules.game.Game;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


class LevelPolicyTest {

    @DisplayName("입력한 목록이 Bronze 조건에 해당합니다.")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideBronze_CardList")
    void isBronzeTest(String description, List<GameCard> list) {
        assertThat(LevelPolicy.isSilver(list)).isFalse();
        assertThat(LevelPolicy.isGold(list)).isFalse();
    }
    private static Stream<Arguments> provideBronze_CardList() {
        Game game1 = new Game("게임1");
        Game game2 = new Game("게임2");
        return Stream.of(
                Arguments.of("소유한 카드가 없습니다.", List.of()),
                Arguments.of("유효카드가 존재하지 않습니다.",
                        List.of(new GameCard("테스트1", 1L, 0, game1),
                                new GameCard("테스트2", 2L, 0, game1),
                                new GameCard("테스트3", 1L, 0, game2)
                        )
                )
        );
    }

    @DisplayName("입력한 목록이 Silver 조건에 해당합니다.")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSilver_CardList")
    void isSilverTest(String description, List<GameCard> list) {
        assertThat(LevelPolicy.isSilver(list)).isTrue();
    }
    @DisplayName("Silver 에 해당하는 카드목록은 Gold 가 아닙니다.")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideSilver_CardList")
    void isNotGolTest(String description, List<GameCard> list) {
        assertThat(LevelPolicy.isGold(list)).isFalse();
    }
    private static Stream<Arguments> provideSilver_CardList() {
        Game game1 = new Game("게임1");
        Game game2 = new Game("게임2");
        return Stream.of(
                Arguments.of("유효카드가 1장입니다.",
                        List.of(new GameCard("테스트1", 1L, 30.0, game1),
                        new GameCard("테스트2", 2L, 0, game1),
                        new GameCard("테스트3", 1L, 0, game2)
                        )
                ),
                Arguments.of("유효카드의 게임 종류가 1개입니다.",
                        List.of(new GameCard("테스트1", 1L, 30.0,game1),
                                new GameCard("테스트2", 2L, 100.0, game1),
                                new GameCard("테스트3", 3L, 10.25, game1)
                        )
                ),
                Arguments.of("서로 다른 2개의 게임인 유효카드가 총 3장이지만, 합이 $99 입니다.",
                        List.of(new GameCard("테스트1", 1L, 30.0,game1),
                        new GameCard("테스트2", 2L, 60.0, game2),
                        new GameCard("테스트3", 3L, 9.99, game1)
                        )
                )
        );
    }

    @DisplayName("입력한 목록이 Gold 조건에 해당합니다.")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideGold_CardList")
    void isGoldTest(String description, List<GameCard> list)  {
        assertThat(LevelPolicy.isGold(list)).isTrue();
    }
    private static Stream<Arguments> provideGold_CardList() {
        Game game1 = new Game("게임1");
        Game game2 = new Game("게임2");
        return Stream.of(
                Arguments.of("서로 다른 2종류 게임의 유효카드가 4장입니다.",
                        List.of(new GameCard("테스트1", 1L, 30.0, game1),
                                new GameCard("테스트2", 2L, 40, game1),
                                new GameCard("테스트3", 1L, 9.1, game2),
                                new GameCard("테스트4", 2L, 5.5, game2),
                                new GameCard("테스트5", 3L, 0, game1)
                        )
                ),
                Arguments.of("서로 다른 2종류 게임의 유효카드가 2장일 때, 합이 $100 이상입니다.",
                        List.of(new GameCard("테스트1", 1L, 30.0,game1),
                                new GameCard("테스트2", 1L, 70.0, game2)
                        )
                ),
                Arguments.of("서로 다른 2개의 게임인 유효카드가 3장일 때, 합이 $100 이상입니다.",
                        List.of(new GameCard("테스트1", 1L, 10.0,game1),
                                new GameCard("테스트2", 1L, 10.0, game2),
                                new GameCard("테스트3", 2L, 80.0, game1)
                        )
                )
        );
    }
}