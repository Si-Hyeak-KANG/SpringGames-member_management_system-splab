package test.splab.springgames.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;
import test.splab.springgames.exception.dto.ExceptionDto;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler(BusinessLogicException.class)
    public ModelAndView handleBusinessLogicException(BusinessLogicException e) {
        ExceptionCode exceptionCode = e.getExceptionCode();
        log.error("reason= [{}] {}", exceptionCode.getName(), exceptionCode.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("exception", ExceptionDto.from(exceptionCode));
        modelAndView.setStatus(e.getExceptionCode().getHttpStatus());
        return modelAndView;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView handleIllegalArgumentException(IllegalArgumentException e) {
        ExceptionCode exceptionCode =
                new BusinessLogicException(ExceptionCode.PARAMETER_BAD_REQUEST).getExceptionCode();
        log.error("reason= [{}] {}", exceptionCode.getName(), exceptionCode.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("exception", ExceptionDto.from(exceptionCode));
        modelAndView.setStatus(exceptionCode.getHttpStatus());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ModelAndView handleIllegalArgumentException(Exception e) {
        ExceptionCode exceptionCode =
                new BusinessLogicException(ExceptionCode.INTERNAL_SERVER_ERROR).getExceptionCode();
        log.error("reason= [{}] {}", exceptionCode.getName(), exceptionCode.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("exception", ExceptionDto.from(exceptionCode));
        modelAndView.setStatus(exceptionCode.getHttpStatus());
        return modelAndView;
    }
}
