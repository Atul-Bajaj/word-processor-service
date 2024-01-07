package com.org.service.wordprocessor.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class HomeController {

    @GetMapping(value = "/v1/dummy")
    public String dummy()
    {
        return "Word Processor Service API welcomes you !!";
    }

}
