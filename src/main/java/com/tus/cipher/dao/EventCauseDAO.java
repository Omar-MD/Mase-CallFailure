package com.tus.cipher.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tus.cipher.dto.sheets.EventCause;

@Repository
public interface EventCauseDAO extends JpaRepository<EventCause, Long> {

	@Query(value = "SELECT DISTINCT event_id, cause_code FROM event_cause", nativeQuery = true)
	List<Object[]> findDistinctEventIdsAndCauseCodes();
}
