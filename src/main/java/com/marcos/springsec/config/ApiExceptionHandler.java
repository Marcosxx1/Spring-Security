package com.marcos.springsec.config;

import com.marcos.springsec.domain.dto.internal.ErrorResponse;
import com.marcos.springsec.exception.CustomHttpException;
import com.marcos.springsec.exception.exeptions.CustomerAlreadyExistsException;
import com.marcos.springsec.exception.exeptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApiExceptionHandler {

    private final MessageSourceAccessor messageSourceAccessor;


    @ExceptionHandler(CustomHttpException.class)
    public ResponseEntity<ErrorResponse> handleCustomHttpException(CustomHttpException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getTitle(), ex.getDetail());
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error(ex.getMessage());
        String title = ex.getTitle();
        String detail = ex.getDetail();
        ErrorResponse errorResponse = new ErrorResponse(title, detail);
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCustomerAlreadyExistsException(CustomerAlreadyExistsException ex) {
        log.error(ex.getMessage());
        String title = ex.getTitle();
        String detail = ex.getDetail();
        ErrorResponse errorResponse = new ErrorResponse(title, detail);
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }
}
