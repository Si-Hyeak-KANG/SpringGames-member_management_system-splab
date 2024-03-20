package test.splab.springgames.modules.card.service;

import test.splab.springgames.exception.BusinessLogicException;
import test.splab.springgames.modules.card.dto.CardEnrollFormDto;

public interface GameCardService {

    /**
     * 입력받은 정보를 바탕으로 카드를 저장합니다.
     * 카드를 소유한 사용자의 카드 전체 개수와 금액을 수정합니다.
     * 정책에 맞게 사용자 레벨을 수정합니다.
     * @param cardEnrollFormDto
     * @throws BusinessLogicException
     *  - MEMBER_NOT_FOUND
     *  - CARD_NOT_FOUND
     */
    void saveNewCard(CardEnrollFormDto cardEnrollFormDto);
}
