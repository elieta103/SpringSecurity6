package com.elhg.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RequestMapping(path = "/accounts")
@RestController
public class AccountsController {

    @GetMapping
    public Map<String, String> accounts(){
        return Collections.singletonMap("msj","accounts");
    }
}
