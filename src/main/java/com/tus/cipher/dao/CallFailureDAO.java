package com.tus.cipher.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tus.cipher.dto.CallFailure;

@Repository
public interface CallFailureDAO extends JpaRepository<CallFailure, Long> {

}
