package com.example.SpringRest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${app.message.dev}")
    String messageDev;
    @Value("${app.message.test}")
    String messageTest;

    @GetMapping("/hello")
    public String helloWorld(){
        System.out.println(messageDev);
        System.out.println(messageTest);
        return "Hello World :)";
    }
}
