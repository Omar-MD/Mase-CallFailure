package com.tus.cipher.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tus.cipher.dto.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>  {
    Optional<Account> findByUsername(String userId);
}
