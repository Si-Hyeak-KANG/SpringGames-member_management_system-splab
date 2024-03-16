package test.splab.springgames.modules.member.service;

import test.splab.springgames.modules.member.dto.EnrollFormDto;
import test.splab.springgames.modules.member.dto.MemberListResultDto;

import java.util.List;

public interface MemberService {

    /**
     * 가입시간 (역순)내림차순으로 정렬하여 조회합니다.
     * (가입시간이 동일할 경우 ID 내림차순 정렬)
     * @return MemberListResultDto - Home page 회원목록에 출력될 내용
     */
    List<MemberListResultDto> getMemberList();

    /**
     * 회원을 등록합니다.
     * @param enrollFormDto - 회원등록시 입력받은 내용
     */
    void saveNewMember(EnrollFormDto enrollFormDto);
}
