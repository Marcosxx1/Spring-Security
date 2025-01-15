package com.marcos.springsec.exception.exeptions;

import com.marcos.springsec.exception.CustomHttpException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Getter
public class CustomerAlreadyExistsException extends CustomHttpException {

    public CustomerAlreadyExistsException(String title, String detail) {
        super(title, detail, HttpStatus.BAD_REQUEST);
    }
}
