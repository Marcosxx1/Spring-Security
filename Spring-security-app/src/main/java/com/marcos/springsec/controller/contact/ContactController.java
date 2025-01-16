package com.marcos.springsec.controller.contact;


import com.marcos.springsec.domain.entity.ContactMessages;

public interface ContactController {

    public ContactMessages saveContactInquiryDetails(ContactMessages contactMessages);
}
