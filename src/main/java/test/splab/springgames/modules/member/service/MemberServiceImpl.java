package test.splab.springgames.modules.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.splab.springgames.modules.member.Member;
import test.splab.springgames.modules.member.dto.MemberListResultDto;
import test.splab.springgames.modules.member.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public List<MemberListResultDto> getMemberList() {
        List<Member> members =
                memberRepository.findAll(Sort.by("joinAt", "memberId").descending());

        return members.stream()
                .map(MemberListResultDto::from)
                .collect(Collectors.toList());
    }
}
