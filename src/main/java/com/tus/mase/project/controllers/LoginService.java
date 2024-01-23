package com.tus.mase.project.controllers;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class LoginService {
    @GetMapping("/login")
    public void SystemAdminHomepage() {
        System.out.println("Login endpoint accessed");
        throw new NotYetImplementedException();
    }
}