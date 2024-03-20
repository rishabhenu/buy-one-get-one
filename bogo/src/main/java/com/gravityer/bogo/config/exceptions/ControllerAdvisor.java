package com.gravityer.bogo.config.exceptions;

import com.gravityer.bogo.dto.GenericResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(ProgramException.class)
    public GenericResponseDto<Void> handleProgramException(ProgramException e) {
        log.error("Exception: {}", e.getMessage());
        return GenericResponseDto.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public GenericResponseDto<Void> handleException(Exception e) {
        log.error("Exception: {}", e.getMessage(), e);
        return GenericResponseDto.<Void>error(e.getMessage()).setMessage("Internal Server Error");
    }

}
