package test.splab.springgames.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import test.splab.springgames.exception.dto.ExceptionDto;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler(BusinessLogicException.class)
    public ModelAndView handleBusinessLogicException(BusinessLogicException e) {
        ExceptionCode exceptionCode = e.getExceptionCode();
        log.info("reason= [{}] {}", exceptionCode.getName(), exceptionCode.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("exception", ExceptionDto.from(exceptionCode));
        modelAndView.setStatus(e.getExceptionCode().getHttpStatus());
        return modelAndView;
    }
}
