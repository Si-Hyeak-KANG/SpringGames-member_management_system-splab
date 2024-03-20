package test.splab.springgames.modules.card.dto.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import test.splab.springgames.modules.card.dto.CardEnrollFormDto;
import test.splab.springgames.modules.card.repository.GameCardRepository;

/**
 * 게임 카드 등록 form 검증
 * 1) 같은 게임에서는 일련 번호가 고유해야 합니다.
 *    - 다른 게임과는 일련 번호가 중복될 수 있습니다.
 * 2) 가격은 소숫점 2자리까지 표현할 수 있습니다.
 */
@Component
@RequiredArgsConstructor
public class CardEnrollFormValidator implements Validator {

    private final GameCardRepository gameCardRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return CardEnrollFormDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CardEnrollFormDto dto = (CardEnrollFormDto) target;
        checkValidSerialNumber(errors, dto.getGameType(), dto.getSerialNumber());
        checkValidPriceDecimalPlace(errors, dto.getPrice());
    }

    private void checkValidPriceDecimalPlace(Errors errors, double price) {
        if (isMoreTwoDecimalPlace(price)) {
            errors.rejectValue(
                    "price",
                    "invalid.price",
                    new Object[]{price},
                    "가격은 소숫점 2자리까지만 허용합니다."
            );
        }
    }

    private boolean isMoreTwoDecimalPlace(double price) {
        String priceToString = String.valueOf(price);
        int indexOfDot = priceToString.indexOf(".");
        return (priceToString.length() - 1) - indexOfDot > 2;
    }

    private void checkValidSerialNumber(Errors errors,String game, Long serialNumber) {
        if (gameCardRepository.existsGameCardWithSerialNumberByGame(game, serialNumber)) {
            errors.rejectValue(
                    "serialNumber",
                    "invalid.serialNumber",
                    new Object[]{serialNumber},
                    "현재 선택한 게임에 동일한 일련번호가 존재합니다."
            );
        }
    }
}
