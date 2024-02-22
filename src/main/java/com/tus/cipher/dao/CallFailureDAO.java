package com.tus.cipher.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tus.cipher.dto.sheets.CallFailure;

@Repository
public interface CallFailureDAO extends JpaRepository<CallFailure, Long> {

	// IMSI
	@Query(value = "SELECT DISTINCT cf.cause_code, cf.event_id, ec.description " +
            "FROM call_failure cf " +
            "INNER JOIN event_cause ec ON cf.cause_code = ec.cause_code AND cf.event_id = ec.event_id " +
            "WHERE cf.imsi = :imsi",
    nativeQuery = true)
	List<Object[]> findImsiEventCauseDescriptions(@Param("imsi") Long imsi);

	@Query(value = "SELECT DISTINCT cf.imsi FROM call_failure cf ", nativeQuery = true)
	List<Long> listImsi();

	// Model
	@Query(value = "SELECT cf.cause_code, cf.event_id, COUNT(*) as failure_count " +
            "FROM call_failure cf " +
            "INNER JOIN event_cause ec ON cf.cause_code = ec.cause_code AND cf.event_id = ec.event_id " +
            "WHERE cf.tac = :tac " +
            "GROUP BY cf.cause_code, cf.event_id",
    nativeQuery = true)
	List<Object[]> findModelsFailureTypesWithCount(@Param("tac") Long tac);

	@Query(value = "SELECT DISTINCT cf.tac FROM call_failure cf ", nativeQuery = true)
	List<Long> listTac();

	// List<CallFailure> findByDateFieldBetween(String startDate, String endDate);
    List<CallFailure> findByDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
}
