package com.elhg.security.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RequestMapping(path = "/loans")
@RestController
public class LoansController {

    @GetMapping
    public Map<String, String> loans(){
        return Collections.singletonMap("msj","loans");
    }
}
