package com.marcos.springsec.exception.util;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class CommonMessageSource extends ReloadableResourceBundleMessageSource {
    public CommonMessageSource() {
        setBasename("classpath:messages");
        setDefaultEncoding("UTF-8");
    }
}
