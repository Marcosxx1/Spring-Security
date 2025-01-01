package com.marcos.springsec.controller.contact;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactControllerImpl implements  ContactController{

    @Override
    @GetMapping("/contacts")
    public String saveContactInquiryDetails() {
        return "inquiry details saved on DB";
    }
}
