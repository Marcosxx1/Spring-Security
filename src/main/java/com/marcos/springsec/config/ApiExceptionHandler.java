package com.marcos.springsec.config;

import com.marcos.springsec.domain.dto.errors.ErrorResponse;
import com.marcos.springsec.domain.dto.errors.ValidationErrorResponse;
import com.marcos.springsec.exception.CustomHttpException;
import com.marcos.springsec.exception.exeptions.CustomerAlreadyExistsException;
import com.marcos.springsec.exception.exeptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.marcos.springsec.exception.ErrorMessages.VALIDATION_ERROR_DETAIL;
import static com.marcos.springsec.exception.ErrorMessages.VALIDATION_ERROR_TITLE;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApiExceptionHandler {

    private final MessageSourceAccessor messageSourceAcessor;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){

        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = exception.getBindingResult().getAllErrors();

        for(ObjectError error : validationErrorList){
            String fieldName = ((FieldError) error).getField();
            String validationMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMessage);
        }

        var validationErrorResponse = new ValidationErrorResponse(
                messageSourceAcessor.getMessage(VALIDATION_ERROR_TITLE),
                messageSourceAcessor.getMessage(VALIDATION_ERROR_DETAIL),
                validationErrors);
        return new ResponseEntity<>(validationErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
