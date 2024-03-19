package com.tus.cipher.dto;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.tus.cipher.dto.accounts.Account;

@SuppressWarnings("serial")
public class AccountDetails implements UserDetails {

    private String name;
    private String password;
    private List<GrantedAuthority> authorities;

    public AccountDetails(Account account) {
        name = account.getUsername();
        password = account.getPassword();
        authorities = List.of(account.getRole().toString()).stream()
        		 .map(SimpleGrantedAuthority::new)
                 .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}