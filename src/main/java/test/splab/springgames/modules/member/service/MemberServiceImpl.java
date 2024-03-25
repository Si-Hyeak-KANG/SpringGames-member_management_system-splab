package test.splab.springgames.modules.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.splab.springgames.exception.BusinessLogicException;
import test.splab.springgames.exception.ExceptionCode;
import test.splab.springgames.modules.card.repository.GameCardRepository;
import test.splab.springgames.modules.member.Level;
import test.splab.springgames.modules.member.Member;
import test.splab.springgames.modules.member.dto.EditFormDto;
import test.splab.springgames.modules.member.dto.EnrollFormDto;
import test.splab.springgames.modules.member.dto.MemberDetailResultDto;
import test.splab.springgames.modules.member.dto.GetMemberListResultDto;
import test.splab.springgames.modules.member.repository.MemberRepository;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final GameCardRepository gameCardRepository;

    @Override
    public GetMemberListResultDto getMemberListByParamAndPage(Level level, String name, PageRequest pageInfo) {
        Page<Member> members;
        if (level == null && name == null) {
            members = memberRepository.findAll(pageInfo);
        } else if (level != null && name != null) {
            members = memberRepository.findAllByLevelAndNameContaining(level, name, pageInfo);
        } else if (level != null) {
            members = memberRepository.findAllByLevel(level, pageInfo);
        } else {
            members = memberRepository.findAllByNameContaining(name, pageInfo);
        }
        return GetMemberListResultDto.from(members);
    }

    @Transactional
    @Override
    public void saveNewMember(EnrollFormDto enrollFormDto) {
        Member newMember = enrollFormDto.toEntity();
        memberRepository.save(newMember);
    }

    @Override
    public MemberDetailResultDto getMemberDetailById(Long id) {
        return MemberDetailResultDto.from(findExistMemberWithCardById(id));
    }

    @Override
    public EditFormDto getMemberEditFormById(Long id) {
        return EditFormDto.from(findExistMemberById(id));
    }

    @Transactional
    @Override
    public void updateMemberFromEditForm(EditFormDto editFormDto) {
        Member member = findExistMemberById(editFormDto.getId());
        member.updateInfo(editFormDto);
    }

    @Override
    public void isExistedMemberById(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
    }

    @Transactional
    @Override
    public void removeMemberAndAllCardByMember(Long id) {
        Member member = findExistMemberWithCardById(id);
        gameCardRepository.deleteAllByMember(member);
        memberRepository.deleteById(id);
    }

    // 사용자만 조회
    private Member findExistMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    // 사용자 조회 fetch join GameCard, Game
    private Member findExistMemberWithCardById(Long id) {
        return memberRepository.findMemberWithGameCardListByMemberId(id)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }
}
