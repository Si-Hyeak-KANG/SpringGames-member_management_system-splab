package test.splab.springgames.modules.member.dto.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import test.splab.springgames.modules.member.dto.EnrollFormDto;
import test.splab.springgames.modules.member.repository.MemberRepository;

import java.time.LocalDate;

/**
 * 회원 등록 form 검증
 * 1) email 중복체크
 * 2) 가입날짜 유효기간 체크 (현재 ~ 1년 이내)
 */
@Component
@RequiredArgsConstructor
public class EnrollFormValidator implements Validator {

    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return EnrollFormDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EnrollFormDto dto = (EnrollFormDto) target;
        checkExistEmail(errors, dto.getEmail());
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
                    "가입일자는 시스템에 등록되는 날 또는 그 이전 1년 이내의 날짜만 허용됩니다."
            );
        }
    }

    private boolean withinOneYearFromNow(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        LocalDate oneYearAgo = currentDate.minusYears(1);
        return date.isAfter(oneYearAgo) && date.isBefore(currentDate);
    }

    private void checkExistEmail(Errors errors, String email) {
        if(memberRepository.existsByEmail(email)) {
            errors.rejectValue(
                    "email",
                    "invalid.email",
                    new Object[]{email},
                    "이미 사용중인 이메일입니다."
            );
        }
    }
}
