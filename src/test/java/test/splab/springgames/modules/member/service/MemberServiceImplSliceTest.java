package test.splab.springgames.modules.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.splab.springgames.exception.BusinessLogicException;
import test.splab.springgames.exception.ExceptionCode;
import test.splab.springgames.modules.member.repository.MemberRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplSliceTest {

    @Mock private MemberRepository memberRepository;
    @InjectMocks private MemberServiceImpl memberService;

    @DisplayName("존재하지 않은 사용자 조회시 BusinessLogicException 발생")
    @Test
    void getMemberDetailById() {

        given(memberRepository.findMemberWithGameCardListByMemberId(anyLong()))
                .willReturn(Optional.empty());

        Long nonExistId = anyLong();
        assertThatThrownBy(() -> memberService.getMemberDetailById(nonExistId))
                .isInstanceOf(BusinessLogicException.class);

        BusinessLogicException actual = assertThrows(BusinessLogicException.class, () ->
                memberService.getMemberDetailById(nonExistId));
        assertEquals(ExceptionCode.MEMBER_NOT_FOUND.getName(), actual.getExceptionCode().getName());
    }
}