package com.tus.cipher.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tus.cipher.dao.AccountRepository;
import com.tus.cipher.dto.accounts.Account;
import com.tus.cipher.dto.accounts.AccountFactory;
import com.tus.cipher.services.ImportService;

@RestController
public class SysAdminController {

	private static final String FILE_NAME = "TUSGroupProject_SampleDataset.xls";

	@Autowired
    AccountRepository accountRepository;

	@PostMapping("/import")
	public void importData() {
		try {
			ImportService.readExcel(FILE_NAME);

		} catch (IOException e) {

			throw new RuntimeException("Failed to import data: " + e.getMessage(), e);
		}

	}

	@PostMapping("/accounts")
    public ResponseEntity<Account> addAccount(@Valid @RequestBody Account account) {
        account = AccountFactory.createAccount(account);
        Account savedAccoount = accountRepository.save(account);
        return ResponseEntity.status(HttpStatus.OK).body(savedAccoount);
    }
}
