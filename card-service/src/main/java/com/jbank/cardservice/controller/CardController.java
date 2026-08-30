package com.jbank.cardservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cards")
public class CardController {

    @GetMapping
    public String name(){
        System.out.println("Hello");
        return "Hello";
    }
}
