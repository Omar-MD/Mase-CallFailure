package com.tus.cipher.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tus.cipher.dto.sheets.MccMnc;

@Repository
public interface MccMncDAO extends JpaRepository<MccMnc, Long> {

}
