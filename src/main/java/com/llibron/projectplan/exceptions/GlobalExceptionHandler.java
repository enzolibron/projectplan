package com.llibron.projectplan.exceptions;

import com.llibron.projectplan.exceptions.custom.InvalidTaskRequestException;
import com.llibron.projectplan.exceptions.custom.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex)
    {
        log.error(ex.getMessage(),ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setHttpStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimeStamp(ex.getTimestamp());
        return errorResponse;
    }

    @ExceptionHandler(InvalidTaskRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleInvalidTaskRequestException(InvalidTaskRequestException ex)
    {
        log.error(ex.getMessage(),ex);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimeStamp(ex.getTimestamp());
        return errorResponse;
    }
}
