package test.splab.springgames.modules.member.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import test.splab.springgames.modules.member.level.Level;
import test.splab.springgames.modules.member.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 회원 목록에 노출되는 회원 정보 DTO입니다.
 */
@Getter
@Builder
public class GetMemberListResultDto {

    List<MemberList> memberList;

    int totalPages;
    int pageNumber;

    public static GetMemberListResultDto from(Page<Member> pageInfo) {

        pageInfo.getTotalPages();
        pageInfo.getPageable().getPageNumber();

        return GetMemberListResultDto.builder()
                .memberList(pageInfo.get().map(MemberList::from).collect(Collectors.toList()))
                .totalPages(pageInfo.getTotalPages())
                .pageNumber(pageInfo.getPageable().getPageNumber()+1)
                .build();
    }

    @Getter
    @Builder
    static class MemberList {

        private Long id;
        private String name;
        private String email;
        private LocalDate joinAt;
        private Level level;
        private int cardTotalCount;

        public static MemberList from(Member member) {
            return MemberList.builder()
                    .id(member.getMemberId())
                    .name(member.getName())
                    .email(member.getEmail())
                    .joinAt(member.getJoinAt())
                    .level(member.getLevel())
                    .cardTotalCount(member.getCardTotalCount())
                    .build();
        }
    }
}
