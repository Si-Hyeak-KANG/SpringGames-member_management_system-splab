package test.splab.springgames.modules.member.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import test.splab.springgames.infra.MockMvcTest;
import test.splab.springgames.modules.member.Member;
import test.splab.springgames.modules.member.dto.EnrollFormDto;
import test.splab.springgames.modules.member.repository.MemberRepository;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
class MemberControllerTest {

    public static final String MAIN_HOME_URL = "/";
    public static final String LENGTH_100 = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuv";
    public static final String LENGTH_101 = LENGTH_100 + "w";

    @Autowired MockMvc mockMvc;
    @Autowired MemberRepository memberRepository;

    @DisplayName("[view]회원등록 페이지 조회 성공")
    @Test
    void getMemberEnrollPage() throws Exception {
        mockMvc.perform(get("/member/enroll"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/enroll"))
                .andExpect(model().attributeExists("enrollFormDto"));
    }

    @Transactional
    @DisplayName("회원등록 성공")
    @ParameterizedTest(name = "{0}")
    @MethodSource("provideCorrectEnrollForm")
    void enrollMemberForm(String description, EnrollFormDto actual) throws Exception {
        mockMvc.perform(post("/member/enroll")
                        .param("name", actual.getName())
                        .param("email", actual.getEmail())
                        .param("joinAt", actual.getJoinAt()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(MAIN_HOME_URL))
                .andExpect(flash().attributeExists("message"));

        // transaction rollback 으로 인해서, 현재 테스트의 mockMvc로 저장한 데이터 1개만 존재합니다.
        Member expected = memberRepository.findAll().stream().findFirst().get();
        assertEquals(expected.getEmail(), actual.getEmail());
    }

    private static Stream<Arguments> provideCorrectEnrollForm() {
        return Stream.of(
                Arguments.of("현재시간 가입",new EnrollFormDto("name", "test1@email.com", LocalDate.now().toString())),
                Arguments.of("1년이내 가입",new EnrollFormDto("name", "test2@email.com", LocalDate.now().minusYears(1).toString())),
                Arguments.of("두글자 한글 이름",new EnrollFormDto("두자", "test3@email.com", LocalDate.now().toString())),
                Arguments.of("100글자 영문 이름",new EnrollFormDto(LENGTH_100, "test4@email.com", LocalDate.now().toString()))
        );
    }

    @DisplayName("회원등록 실패 - 이름은 2자 미만 100자 초과, 한글 영어 외에 문자, 공백")
    @ParameterizedTest
    @ValueSource(strings = {"자", LENGTH_101,"-*!@#", "1234"})
    @NullAndEmptySource
    void enrollMemberForm_fail_invalid_name(String name) throws Exception {
        mockMvc.perform(post("/member/enroll")
                        .param("name", name)
                        .param("email", "test@email.com")
                        .param("joinAt", LocalDate.now().toString()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("member/enroll"))
                .andExpect(model().attributeHasFieldErrors("enrollFormDto","name"));
    }

    @DisplayName("회원등록 실패 - 중복 이메일, 잘못된 형식, 공백")
    @ParameterizedTest
    @ValueSource(strings = {"already@email.com", "email"})
    @NullAndEmptySource
    void enrollMemberForm_fail_invalid_email(String email) throws Exception {

        memberRepository.save(Member.of("first", "already@email.com",LocalDate.now()));

        mockMvc.perform(post("/member/enroll")
                        .param("name", "name")
                        .param("email", email)
                        .param("joinAt", LocalDate.now().toString()))
                .andExpect(model().hasErrors())
                .andExpect(view().name("member/enroll"))
                .andExpect(model().attributeHasFieldErrors("enrollFormDto","email"));
    }

    @DisplayName("회원등록 실패 - 가입날짜가 현재 또는 1년 이내가 아닌 경우, 공백, 잘못된 포맷")
    @ParameterizedTest(name = "{0} : {1}")
    @MethodSource("provideWrongJoinAt")
    void enrollMemberForm_fail_invalid_joinAt(String description,String joinAt) throws Exception {
        mockMvc.perform(post("/member/enroll")
                        .param("name", "name")
                        .param("email", "test@email.com")
                        .param("joinAt", joinAt))
                .andExpect(model().hasErrors())
                .andExpect(view().name("member/enroll"))
                .andExpect(model().attributeHasFieldErrors("enrollFormDto","joinAt"));
    }

    private static Stream<Arguments> provideWrongJoinAt() {
        return Stream.of(
                Arguments.of("현재보다 1일 이후",LocalDate.now().plusDays(1).toString()),
                Arguments.of("1년보다 이전",LocalDate.now().minusYears(1).minusDays(1).toString()),
                Arguments.of("잘못된 포맷","2024.01.30"),
                Arguments.of("잘못된 포맷","2024-1-1"),
                Arguments.of("잘못된 포맷","24-1-1"),
                Arguments.of("공백"," "),
                Arguments.of("공백",null)
        );
    }
}