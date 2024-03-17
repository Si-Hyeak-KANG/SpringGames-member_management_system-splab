package test.splab.springgames.modules.member.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import test.splab.springgames.modules.member.Member;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollFormDto {

    @NotBlank(message = "이름은 공백은 포함될 수 있으나 전체가 공백일 수 없습니다.")
    @Size(min = 2, max = 100, message = "이름은 최소 2자리 이상 최대 100자리 이하입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z]+$",message = "이름은 한글 또는 영문만 허용합니다.")
    String name;

    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    @Email(message = "이메일 형식에 맞아야 합니다.")
    String email;

    @NotBlank(message = "가입날짜는 공백일 수 없습니다.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "가입날짜는 YYYY-MM-DD 형태로 작성해야 합니다. (예시: 2024-03-15)")
    String joinAt;

    public Member toEntity() {
        return Member.of(this.name, this.email, getJoinAtToLocalDate());
    }

    public LocalDate getJoinAtToLocalDate() {
        return LocalDate.parse(joinAt, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public boolean isNotInvalidJoinAt() {
        return this.joinAt!=null && this.joinAt.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }
}
