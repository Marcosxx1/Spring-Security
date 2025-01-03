package com.marcos.springsec.exception;

import com.marcos.springsec.exception.exeptions.CustomerAlreadyExistsException;
import com.marcos.springsec.exception.exeptions.ResourceNotFoundException;
import com.marcos.springsec.exception.util.MessageSourceAcessorLocal;
import org.springframework.context.support.MessageSourceAccessor;

import static com.marcos.springsec.exception.ErrorMessages.*;

public class ExceptionFactory {

    private static final MessageSourceAccessor messageSourceAccessor = new MessageSourceAcessorLocal();

    private ExceptionFactory() {}

    public static ResourceNotFoundException resourceNotFoundException() {
        String title = messageSourceAccessor.getMessage(ERROR_RESOURCE_NOT_FOUND_TITLE);
        String detail = messageSourceAccessor.getMessage(ERROR_RESOURCE_NOT_FOUND_DETAIL);
        return new ResourceNotFoundException(title, detail);
    }

    public static CustomerAlreadyExistsException customerAlreadyExistsException(String identifier){
        String title = messageSourceAccessor.getMessage(ERROR_RESOURCE_ALREADY_EXISTS_WITH_IDENTIFIER_TITLE, new Object[] {identifier});
        String detail = messageSourceAccessor.getMessage(ERROR_RESOURCE_ALREADY_EXISTS_WITH_IDENTIFIER_DETAIL, new Object[] {identifier});

        return new CustomerAlreadyExistsException(title, detail);
    }

}
