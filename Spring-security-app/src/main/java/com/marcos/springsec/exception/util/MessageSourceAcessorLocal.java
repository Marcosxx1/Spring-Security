package com.marcos.springsec.exception.util;

import org.springframework.context.support.MessageSourceAccessor;

public class MessageSourceAcessorLocal extends MessageSourceAccessor {

    public MessageSourceAcessorLocal() {
        super(new CommonMessageSource());
    }
}
