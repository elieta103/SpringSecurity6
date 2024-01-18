package com.elhg.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RequestMapping(path = "/cards")
@RestController
public class CardsController {

    @GetMapping
    public Map<String, String> cards(){
        return Collections.singletonMap("msj","cards");
    }
}
