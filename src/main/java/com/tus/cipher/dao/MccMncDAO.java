package com.tus.cipher.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tus.cipher.dto.sheets.MccMnc;

@Repository
public interface MccMncDAO extends JpaRepository<MccMnc, Long> {

	@Query(value = "SELECT DISTINCT mcc, mnc FROM mcc_mnc", nativeQuery = true)
	List<Object[]> findDistinctMccAndMnc();
}
