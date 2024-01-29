package com.tus.cipher.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.RestController;

import com.tus.cipher.dao.AccountRepository;
import com.tus.cipher.dto.accounts.Account;
import com.tus.cipher.dto.accounts.AccountFactory;
import com.tus.cipher.exceptions.AccountNotFoundException;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    // @GetMapping("/accounts/{id}")
	// public ResponseEntity<Account> getOrderById(@PathVariable(value="id") Long accountId) throws AccountNotFoundException {
	// 	Optional<Account> account = accountRepository.findById(accountId);
	// 	if(account.isPresent()) {
	// 		return ResponseEntity.ok().body(account.get());
	// 	} else {
	// 		throw new AccountNotFoundException("Account not found :: " + accountId);
	// 	}
	// }

    @PostMapping("/accounts")
    public ResponseEntity<Account> addAccount(@Valid @RequestBody Account account) {
        account = AccountFactory.createAccount(account);
        Account savedAccoount = accountRepository.save(account);
        return ResponseEntity.status(HttpStatus.OK).body(savedAccoount);
    }
}
