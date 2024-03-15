package test.splab.springgames.modules.member.dto;

import lombok.Builder;
import lombok.Getter;
import test.splab.springgames.modules.member.Level;
import test.splab.springgames.modules.member.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 회원 목록에 노출되는 회원 정보 DTO입니다.
 */
@Getter
@Builder
public class MemberListResultDto {

    private Long id;
    private String name;
    private String email;
    private LocalDate joinAt;
    private Level level;
    private int cardTotalCount;

    public static MemberListResultDto from(Member member) {
        return MemberListResultDto.builder()
                .id(member.getMemberId())
                .name(member.getName())
                .email(member.getEmail())
                .joinAt(member.getJoinAt())
                .level(member.getLevel())
                .cardTotalCount(member.getCardTotalCount())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberListResultDto dto)) return false;
        return cardTotalCount == dto.cardTotalCount
                && Objects.equals(id, dto.id)
                && Objects.equals(name, dto.name)
                && Objects.equals(email, dto.email)
                && Objects.equals(joinAt, dto.joinAt)
                && level == dto.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, joinAt, level, cardTotalCount);
    }
}
