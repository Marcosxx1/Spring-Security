package com.marcos.springsec.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {


    @GetMapping
    public String sayWelcome(){
        return "Welcome to Spring Application with out security";
    }
}
