package test.splab.springgames.modules.card.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import test.splab.springgames.modules.card.GameCard;
import test.splab.springgames.modules.game.Game;
import test.splab.springgames.modules.game.repository.GameRepository;
import test.splab.springgames.modules.member.Member;
import test.splab.springgames.modules.member.repository.MemberRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class GameCardRepositoryTest {

    @Autowired GameCardRepository gameCardRepository;
    @Autowired GameRepository gameRepository;
    @Autowired MemberRepository memberRepository;

    private static final Long TEMP_SERIAL_NUMBER = 1L;

    @DisplayName("[Query] 특정 게임에 해당하는 카드 중 동일한 일련번호가 존재")
    @Test
    void existsGameCardSameSerialNumberByGame_true() {
        //given
        GameCard testCard = GameCard.of("테스트카드", TEMP_SERIAL_NUMBER, 100);
        Member testMember = memberRepository.save(Member.of("테스트", "test@test.com", LocalDate.now()));
        Game testGame = gameRepository.save(new Game("테스트 게임"));
        testCard.addMember(testMember);
        testCard.addGame(testGame);
        gameCardRepository.save(testCard);

        // when
        boolean expected = gameCardRepository.existsGameCardWithSerialNumberByGame(testGame.getName(), TEMP_SERIAL_NUMBER);

        // then
        assertTrue(expected);
    }

    @DisplayName("[Query] 특정 게임에 해당하는 카드 중 동일한 일련번호가 없음")
    @Test
    void existsGameCardSameSerialNumberByGame_false() {
        // given
        Game testGame = gameRepository.save(new Game("테스트 게임"));
        System.out.println(gameRepository.findAll().size());
        boolean expected = gameCardRepository.existsGameCardWithSerialNumberByGame(testGame.getName(), TEMP_SERIAL_NUMBER);
        // then
        assertFalse(expected);
    }
}