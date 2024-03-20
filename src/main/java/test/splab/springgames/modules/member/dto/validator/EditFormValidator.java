package test.splab.springgames.modules.member.dto.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import test.splab.springgames.modules.member.dto.EditFormDto;
import test.splab.springgames.modules.member.repository.MemberRepository;
import test.splab.springgames.modules.member.service.MemberService;

import java.time.LocalDate;

/**
 * 회원 수정 form 검증
 * 1) email 중복체크
 * 2) 가입날짜 유효기간 체크 (수정 시점 현재 ~ 1년 이내)
 */
@Component
@RequiredArgsConstructor
public class EditFormValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return EditFormDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EditFormDto dto = (EditFormDto) target;
        checkExistEmail(errors, dto.getId(), dto.getEmail());
        if(dto.isNotInvalidJoinAt()) {
            checkValidJoinAt(errors, dto.getJoinAtToLocalDate());
        }
    }

    private void checkValidJoinAt(Errors errors, LocalDate joinAt) {
        if (!withinOneYearFromNow(joinAt)) {
            errors.rejectValue(
                    "joinAt",
                    "invalid.joinAt",
                    new Object[]{joinAt},
                    "가입일자는 현재 날짜 또는 그 이전 1년 이내의 날짜만 허용됩니다."
            );
        }
    }

    private boolean withinOneYearFromNow(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        LocalDate oneYearAgo = currentDate.minusYears(1);
        return date.isAfter(oneYearAgo) && date.isBefore(currentDate) || date.isEqual(currentDate) || date.isEqual(oneYearAgo);
    }

    /**
     * 회원의 정보를 수정할 때에는
     * 해당 사용자의 기존 이메일로 유지할 경우 중복체크를 하지 않습니다.
     * ===
     * 1) email 로 조회한 member 가 없으면 패스
     * 2) email 로 조회한 member 와 id로 조회한 member 가 같으면 패스
     * 3) email 로 조회한 member 는 존재하나 id에 해당하는 member 가 아니면 이메일 중복 에러
     */
    private void checkExistEmail(Errors errors, Long id, String email) {

        memberRepository.findMemberByEmail(email).ifPresent(memberByEmail -> {
                if(memberRepository.findById(id).get().equals(memberByEmail)) return;
                else errors.rejectValue(
                        "email",
                        "invalid.email",
                        new Object[]{email},
                        "이미 사용중인 이메일입니다."
                );
        });
    }
}
