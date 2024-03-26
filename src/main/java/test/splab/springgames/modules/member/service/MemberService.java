package test.splab.springgames.modules.member.service;

import org.springframework.data.domain.PageRequest;
import test.splab.springgames.exception.BusinessLogicException;
import test.splab.springgames.modules.member.level.Level;
import test.splab.springgames.modules.member.dto.EditFormDto;
import test.splab.springgames.modules.member.dto.EnrollFormDto;
import test.splab.springgames.modules.member.dto.MemberDetailResultDto;
import test.splab.springgames.modules.member.dto.GetMemberListResultDto;

public interface MemberService {

    /**
     * 가입시간 (역순)내림차순으로 정렬하여 조회합니다.
     * (가입시간이 동일할 경우 ID 내림차순 정렬)
     *
     * @param level    - Level 포함시, 특정 레벨에 해당하는 사용자 목록 조회 (null 이면 경우 전체 조회)
     * @param name     - 이름이 포함시, 이름이 포함된 사용자 목록 조회 (null 이면 경우 전체 조회)
     *                 (level과 name 두 파라미터를 포함하는 데이터를 조회할 수 있어야합니다.)
     * @param pageInfo
     * @return MemberListResultDto - Home page 회원목록에 출력될 내용
     */
    GetMemberListResultDto getMemberListByParamAndPage(Level level, String name, PageRequest pageInfo);

    /**
     * 회원을 등록합니다.
     * @param enrollFormDto - 회원등록시 입력받은 내용
     */
    void saveNewMember(EnrollFormDto enrollFormDto);

    /**
     * ID 에 해당하는 사용자의 정보를 조회합니다.
     * 만약 ID에 해당하는 사용자가 없을 시 예외가 발생합니다.
     * 사용자가 소유한 카드도 함께 조회합니다.
     * @param id 사용자 ID
     * @return MemberDetailResultDto - 회원 조회 페이지에 출력될 내용
     * @throws BusinessLogicException - MEMBER_NOT_FOUND
     */
    MemberDetailResultDto getMemberDetailById(Long id);

    /**
     * ID 에 해당하는 사용자의 정보를 조회합니다.
     * @param id
     * @return EditFormDto - 회원 수정을 위한 정보
     * @throws BusinessLogicException - MEMBER_NOT_FOUND
     */
    EditFormDto getMemberEditFormById(Long id);

    /**
     * 입받은 Form 에 맞게 사용자 정보를 수정합니다.
     * @param editFormDto - 수정하려는 회원 정보
     * @throws BusinessLogicException - MEMBER_NOT_FOUND
     */
    void updateMemberFromEditForm(EditFormDto editFormDto);

    /**
     * Id에 해당하는 사용자가 있는지 여부를 체크합니다.
     * 존재하지 않을 시, 커스텀 Runtime Exception 인 BusinessLogic Exception 이 발생합니다.
     * @param id
     * @throws BusinessLogicException - MEMBER_NOT_FOUND
     */
    void isExistedMemberById(Long id);

    /**
     * Id에 해당하는 사용자를 삭제합니다.
     * 사용자가 소유했던 카드도 모두 삭제합니다.
     * @param id
     * @throws BusinessLogicException - MEMBER_NOT_FOUND
     */
    void removeMemberAndAllCardByMember(Long id);
}
