package test.splab.springgames.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않은 사용자입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    public int getCode() {
        return this.httpStatus.value();
    }

    public String getName() {
        return this.httpStatus.name();
    }
}
