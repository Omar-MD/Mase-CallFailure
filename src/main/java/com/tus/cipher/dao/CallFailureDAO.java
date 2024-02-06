package com.tus.cipher.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tus.cipher.dto.sheets.CallFailure;

@Repository
public interface CallFailureDAO extends JpaRepository<CallFailure, Long> {

	@Query(value = "SELECT DISTINCT cf.cause_code, cf.event_id, ec.description " +
            "FROM call_failure cf " +
            "INNER JOIN event_cause ec ON cf.cause_code = ec.cause_code " +
            "AND cf.event_id = ec.event_id " +
            "WHERE cf.imsi = :imsi",
    nativeQuery = true)
	List<Object[]> findImsiEventCauseDescriptions(@Param("imsi") Long imsi);


	@Query(value = "SELECT DISTINCT cf.imsi FROM call_failure cf ", nativeQuery = true)
	List<Long> listImsi();
}
