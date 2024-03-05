package com.tus.cipher.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tus.cipher.dto.sheets.FailureClass;

@Repository
public interface FailureClassDAO extends JpaRepository<FailureClass, Long> {

	@Query(value = "SELECT DISTINCT failure_code FROM failure_class", nativeQuery = true)
	List<Integer> findDistinctFailureCodes();
}
