package com.tus.cipher.controllers;

import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.tus.cipher.dao.AccountRepository;
import com.tus.cipher.dto.LoginRequest;
import com.tus.cipher.dto.accounts.Account;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
public class LoginController {
    @Autowired
    AccountRepository accountRepository;

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody LoginRequest loginDetail) {

        System.out.println("Login endpoint accessed: " + loginDetail.getUsername() + ", pass: " + loginDetail.getPassword());
        
        String username = loginDetail.getUsername();
        String password = loginDetail.getPassword();

        Optional<Account> account = accountRepository.findByUsername(username);

        if(account.isPresent()) {
            Account loginAccount = account.get();
            

            if(loginAccount.getPassword().equals(password)) {
                // Code for successful login
                System.out.println("Logged in successfully");
                return new ResponseEntity<Account>(loginAccount, HttpStatus.OK);
            } else {
                // Code for incorrect password
                System.out.println("Wrong password");
                return new ResponseEntity<Account>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            // Code for invalid username
            System.out.println("Account not found");
            return new ResponseEntity<Account>(HttpStatus.UNAUTHORIZED);
            
        }
    }
}
