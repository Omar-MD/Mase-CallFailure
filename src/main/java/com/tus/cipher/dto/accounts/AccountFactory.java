package com.tus.cipher.dto.accounts;

public class AccountFactory {
    private AccountFactory() {}

    public static Account createAccount(Account account) {
        return createAccount(account.getUsername(), account.getPassword(), account.getRole());
    }

    public static Account createAccount(String username, String password, String role) {
        return new Account(null, username, password, role);
        // switch(role) {
        //     case "SysAdm":
        //         return new SysAdm(null, username, password, role);
        //     case "NetEng":
        //         return new NetEng(null, username, password, role);
        //     case "SuppEng":
        //         return new SuppEng(null, username, password, role);
        //     case "CustomerRep":
        //         return new CustomerRep(null, username, password, role);
        //     default:
        //         return null;
        // }
    }
}
