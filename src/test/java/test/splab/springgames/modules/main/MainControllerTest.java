package test.splab.springgames.modules.main;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import test.splab.springgames.infra.MockMvcTest;
import test.splab.springgames.modules.member.Member;
import test.splab.springgames.modules.member.dto.MemberListResultDto;
import test.splab.springgames.modules.member.repository.MemberRepository;
import test.splab.springgames.modules.member.service.MemberService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
class MainControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired MemberRepository memberRepository;
    @Autowired MemberService memberService;

    @BeforeEach
    void beforeEach() {
        List<Member> members = new ArrayList<>(List.of(
                Member.of("test1", "test1@email.com", LocalDateTime.now()),
                Member.of("test2", "test2@email.com", LocalDateTime.now().plusMinutes(1)),
                Member.of("test3", "test3@email.com", LocalDateTime.now().plusMinutes(2))
        ));
        memberRepository.saveAll(members);
    }

    @DisplayName("메인 화면-회원목록 조회 성공")
    @Test
    void home() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("memberList"))
                .andExpect(model().attribute("memberList", memberService.getMemberList()));
    }
}