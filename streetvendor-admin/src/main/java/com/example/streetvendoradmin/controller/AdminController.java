package com.example.streetvendoradmin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("/admin/ping")
    public String ping(){
        return "pong";
    }


}
