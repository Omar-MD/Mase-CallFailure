package com.tus.cipher.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginService {
    @GetMapping("/login")
    public void SystemAdminHomepage() {
        System.out.println("Login endpoint accessed");
//        throw new NotYetImplementedException();
    }
}