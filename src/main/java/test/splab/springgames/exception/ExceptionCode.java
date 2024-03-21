package test.splab.springgames.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
    GAME_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게임입니다."),
    CARD_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 카드입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getCode() {
        return this.httpStatus.value();
    }

    public String getName() {
        return this.httpStatus.name();
    }
}
