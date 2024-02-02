package com.tus.cipher.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tus.cipher.dto.sheets.Ue;

@Repository
public interface UeDAO extends JpaRepository<Ue, Long> {

	@Query(value = "SELECT DISTINCT tac FROM ue", nativeQuery = true)
	List<Long> findDistinctUe();
}
