package com.tus.cipher.dto.accounts;

public class AccountFactory {
    private AccountFactory() {}

    public static Account createAccount(Account account) {
        return createAccount(account.getUsername(), account.getPassword(), account.getRole());
    }

    public static Account createAccount(String username, String password, String role) {
        return new Account(username, password, role);
    }
}
