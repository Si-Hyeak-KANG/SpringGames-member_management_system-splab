package test.splab.springgames.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import test.splab.springgames.exception.ExceptionCode;

@Getter
@AllArgsConstructor
public class ExceptionDto {

    int code;
    String name;
    String message;

    public static ExceptionDto from(ExceptionCode e) {
        return new ExceptionDto(e.getCode(), e.getName(), e.getMessage());
    }
}
