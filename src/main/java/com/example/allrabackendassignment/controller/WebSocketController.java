package com.example.allrabackendassignment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebSocketController {

    @GetMapping("/ws")
    public String ws() {
        return "test";
    }
}
