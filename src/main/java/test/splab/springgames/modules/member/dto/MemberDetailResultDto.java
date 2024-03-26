package test.splab.springgames.modules.member.dto;

import lombok.Builder;
import lombok.Getter;
import test.splab.springgames.modules.member.level.Level;
import test.splab.springgames.modules.member.Member;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class MemberDetailResultDto {

    Long id;
    String name;
    String email;
    String joinAt;
    Level level;
    int cardTotalCount;
    double cardTotalPrice;

    List<GameCardsOfMemberDto> gameCardsList;

    public static MemberDetailResultDto from(Member member) {
        return MemberDetailResultDto.builder()
                .id(member.getMemberId())
                .name(member.getName())
                .email(member.getEmail())
                .joinAt(member.getJoinAt().toString())
                .level(member.getLevel())
                .cardTotalCount(member.getCardTotalCount())
                .cardTotalPrice(member.getCardTotalPrice())
                .gameCardsList(member.getGameCardList().stream()
                        .map(GameCardsOfMemberDto::from)
                        .collect(Collectors.toList())
                )
                .build();
    }
}
