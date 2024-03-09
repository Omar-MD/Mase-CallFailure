package com.tus.cipher.jwt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tus.cipher.dao.AccountRepository;
import com.tus.cipher.dto.AccountDetails;
import com.tus.cipher.dto.accounts.Account;

@Service
public class AccountService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Account> account = accountRepo.findByUsername(username);
		return account.map(AccountDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}

	public String loadUserRole(String username) throws UsernameNotFoundException {

		Optional<Account> account = accountRepo.findByUsername(username);
		return account.map(acc -> acc.getRole().toString()).orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}
}