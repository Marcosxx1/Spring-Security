package com.marcos.springsec.controller.contact;

import com.marcos.springsec.domain.entity.ContactMessages;
import com.marcos.springsec.service.contact.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContactControllerImpl implements ContactController {

    private final ContactService contactService;

    @Override
    @GetMapping("/contacts")
    public ContactMessages saveContactInquiryDetails(@RequestBody ContactMessages contactMessages) { // Criar SaveContactMessagesRequest
        return contactService.saveContactInquiryDetails(contactMessages);
    }
}
