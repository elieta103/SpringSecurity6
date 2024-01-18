package com.elhg.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RequestMapping(path = "/welcome")
@RestController
public class WelcomeController {

    @GetMapping
    public Map<String, String> welcome(){
        return Collections.singletonMap("msj","welcome");
    }
}
