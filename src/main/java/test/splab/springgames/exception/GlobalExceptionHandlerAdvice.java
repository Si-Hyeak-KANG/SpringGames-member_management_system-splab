package test.splab.springgames.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import test.splab.springgames.exception.dto.ExceptionDto;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler(BusinessLogicException.class)
    public String handleBusinessLogicException(BusinessLogicException e, Model model) {
        ExceptionCode exceptionCode = e.getExceptionCode();
        log.info("reason= [{}] {}", exceptionCode.getName(), exceptionCode.getMessage());
        model.addAttribute("exception", ExceptionDto.from(exceptionCode));
        return "error";
    }
}
