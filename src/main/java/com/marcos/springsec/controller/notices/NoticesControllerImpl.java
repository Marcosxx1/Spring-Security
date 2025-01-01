package com.marcos.springsec.controller.notices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticesControllerImpl implements NoticesController{


    @Override
    @GetMapping("/notices")
    public String getNotices() {
        return "Here are the notices details from the DB";
    }
}
