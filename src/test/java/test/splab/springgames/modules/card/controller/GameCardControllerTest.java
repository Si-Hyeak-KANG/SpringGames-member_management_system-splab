package test.splab.springgames.modules.card.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import test.splab.springgames.infra.MockMvcTest;
import test.splab.springgames.modules.card.GameCard;
import test.splab.springgames.modules.card.dto.CardEnrollFormDto;
import test.splab.springgames.modules.card.repository.GameCardRepository;
import test.splab.springgames.modules.game.Game;
import test.splab.springgames.modules.game.repository.GameRepository;
import test.splab.springgames.modules.member.Member;
import test.splab.springgames.modules.member.repository.MemberRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static test.splab.springgames.modules.card.controller.GameCardController.CARD_ENROLL_SUCCESS_MESSAGE;

@MockMvcTest
class GameCardControllerTest {

    private static final String COMMON_URL = "/card";
    private static final String LENGTH_100 = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuv";
    private static final String LENGTH_101 = LENGTH_100 + "w";

    @Autowired MockMvc mockMvc;
    @Autowired MemberRepository memberRepository;
    @Autowired GameRepository gameRepository;
    @Autowired GameCardRepository gameCardRepository;

    @BeforeEach
    void beforeEach() {
        memberRepository.save(Member.of("test1", "test1@email.com", LocalDate.now()));
        gameRepository.save(new Game("테스트용 게임"));
    }

    @DisplayName("[view]카드등록 페이지 조회 성공")
    @Test
    void getMemberEnrollPage() throws Exception {
        Long existMemberId = memberRepository.findAll().stream().findFirst().get().getMemberId();
        mockMvc.perform(get(COMMON_URL + "/enroll/member/{member-id}", existMemberId))
                .andExpect(status().isOk())
                .andExpect(view().name("card/enroll"))
                .andExpect(model().attributeExists("cardEnrollFormDto"))
                .andExpect(model().attributeExists("gameList"));
    }

    @DisplayName("카드 등록 성공-사용자가 소유한 총 카드 수와 금액도 등록한 카드에 맞게 증가합니다.")
    @Test
    void enrollCardFormTest_success() throws Exception {
        Member existMember = memberRepository.findAll().stream().findFirst().get();
        Game existGame = gameRepository.findAll().stream().findFirst().get();
        CardEnrollFormDto actual = new CardEnrollFormDto(
                existMember.getMemberId(),
                existGame.getName(),
                "카드",
                123L,
                10.01);

        double preCardTotalPrice = existMember.getCardTotalPrice();
        int preCardTotalCount = existMember.getCardTotalCount();

        // when
        mockMvc.perform(post(COMMON_URL+"/enroll")
                        .param("memberId", String.valueOf(actual.getMemberId()))
                        .param("gameType", actual.getGameType())
                        .param("title", actual.getTitle())
                        .param("serialNumber", String.valueOf(actual.getSerialNumber()))
                        .param("price", String.valueOf(actual.getPrice())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/member/detail/"+actual.getMemberId()))
                .andExpect(flash().attributeExists("message"))
                .andExpect(flash().attribute("message", CARD_ENROLL_SUCCESS_MESSAGE));

        // 카드 저장 여부
        GameCard expected = gameCardRepository.findAll().stream().findFirst().get();
        assertEquals(expected.getTitle(), actual.getTitle());

        // 사용자 소유 카드관련 정보 변경여부
        assertEquals(existMember.getCardTotalCount(), preCardTotalCount+1);
        assertEquals(existMember.getCardTotalPrice(), preCardTotalPrice+actual.getPrice());
    }

    @DisplayName("카드등록 실패 - 타이틀은 공백이 아닌, 1자 이상 100자 이하 문자만 허용합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"", LENGTH_101})
    @NullAndEmptySource
    void enrollCardFormTest_fail_invalid_title(String title) throws Exception {
        Member existMember = memberRepository.findAll().stream().findFirst().get();
        Game existGame = gameRepository.findAll().stream().findFirst().get();
        CardEnrollFormDto actual = new CardEnrollFormDto(
                existMember.getMemberId(),
                existGame.getName(),
                title,
                123L,
                10.01);

        // when
        mockMvc.perform(post(COMMON_URL+"/enroll")
                        .param("memberId", String.valueOf(actual.getMemberId()))
                        .param("gameType", actual.getGameType())
                        .param("title", actual.getTitle())
                        .param("serialNumber", String.valueOf(actual.getSerialNumber()))
                        .param("price", String.valueOf(actual.getPrice())))
                .andExpect(model().hasErrors())
                .andExpect(view().name("card/enroll"))
                .andExpect(model().attributeHasFieldErrors("cardEnrollFormDto","title"));
    }

}