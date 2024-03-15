package test.splab.springgames.modules.member.dto;

import lombok.Builder;
import lombok.Getter;
import test.splab.springgames.modules.member.Level;
import test.splab.springgames.modules.member.Member;

import java.time.LocalDateTime;

/**
 * 회원 목록에 노출되는 회원 정보 DTO입니다.
 */
@Getter
@Builder
public class MemberListResultDto {

    private Long id;
    private String name;
    private String email;
    private LocalDateTime joinAt;
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
}
