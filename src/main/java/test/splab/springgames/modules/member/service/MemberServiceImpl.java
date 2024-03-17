package test.splab.springgames.modules.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.splab.springgames.modules.member.Member;
import test.splab.springgames.modules.member.dto.EnrollFormDto;
import test.splab.springgames.modules.member.dto.MemberDetailResultDto;
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

    @Transactional
    @Override
    public void saveNewMember(EnrollFormDto enrollFormDto) {
        Member newMember = enrollFormDto.toEntity();
        memberRepository.save(newMember);
    }

    @Override
    public MemberDetailResultDto getMemberDetailById(Long id) {
        Member member = findExistMemberById(id);
        return MemberDetailResultDto.from(member);
    }

    private Member findExistMemberById(Long id) {
        return memberRepository.findMemberWithGameCardListByMemberId(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}
