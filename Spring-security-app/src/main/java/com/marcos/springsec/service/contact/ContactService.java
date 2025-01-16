package com.marcos.springsec.service.contact;

import com.marcos.springsec.domain.entity.ContactMessages;

public interface ContactService {
    ContactMessages saveContactInquiryDetails(ContactMessages contactMessages);
}
