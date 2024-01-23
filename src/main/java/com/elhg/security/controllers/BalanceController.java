package com.elhg.security.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RequestMapping(path = "/balance")
@RestController
public class BalanceController {

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping
    public Map<String, String> balance(){
        return Collections.singletonMap("msj","balance");
    }
}
