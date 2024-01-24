package com.tus.cipher.controllers;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.web.bind.annotation.RestController;

import com.tus.cipher.dto.LoginRequest;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class LoginService {
    @GetMapping("/login")
    public void SystemAdminHomepage(@RequestBody LoginRequest loginDetail) {
    	
        System.out.println("Login endpoint accessed: " + loginDetail.getUsername() + ", pass: " loginDetail.getPassword());
        throw new NotYetImplementedException();
    }
}