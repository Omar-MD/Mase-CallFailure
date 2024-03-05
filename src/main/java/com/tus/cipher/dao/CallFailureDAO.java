package com.tus.cipher.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tus.cipher.dto.sheets.CallFailure;

@Repository
public interface CallFailureDAO extends JpaRepository<CallFailure, Long> {

	@Query(value = "SELECT DISTINCT cf.imsi FROM call_failure cf ", nativeQuery = true)
	List<Long> listImsi();

	@Query(value = "SELECT DISTINCT cf.tac FROM call_failure cf ", nativeQuery = true)
	List<Long> listTac();


	// Query #1
	@Query(value = "SELECT DISTINCT cf.cause_code, cf.event_id, ec.description " + "FROM call_failure cf "
			+ "INNER JOIN event_cause ec ON cf.cause_code = ec.cause_code AND cf.event_id = ec.event_id "
			+ "WHERE cf.imsi = :imsi", nativeQuery = true)
	List<Object[]> findImsiEventCauseDescriptions(@Param("imsi") Long imsi);


	// Query #2
    @Query("SELECT COUNT(c) FROM CallFailure c WHERE c.imsi = :imsi AND c.dateTime BETWEEN :startDate AND :endDate")
    long countByImsiAndDateTimeBetween(@Param("imsi") Long imsi, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate );


    //Query #3
	@Query("SELECT DISTINCT c.imsi FROM CallFailure c WHERE c.dateTime BETWEEN :startDate AND :endDate")
	List<Long> findDistinctImsiByDateTimeBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


	// Query #4
	@Query(value = "SELECT COUNT (cf.tac) FROM call_failure cf WHERE cf.tac = :tac and cf.date_time BETWEEN :startDate AND :endDate", nativeQuery = true)
	Long getModelFaliureCount(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("tac") Long tac );

	// query 4.5
	@Query(value = "SELECT DISTINCT (cf.imsi) FROM call_failure cf WHERE cf.failure_code = :failureClass", nativeQuery = true)
	List<Long> getIMSIsWithFailureClass(@Param("failureClass") Long failureClass);

	// Query #5
	@Query(value = "SELECT cf.cause_code, cf.event_id, COUNT(*) as failure_count " + "FROM call_failure cf "
			+ "INNER JOIN event_cause ec ON cf.cause_code = ec.cause_code AND cf.event_id = ec.event_id "
			+ "WHERE cf.tac = :tac " + "GROUP BY cf.cause_code, cf.event_id", nativeQuery = true)
	List<Object[]> findModelsFailureTypesWithCount(@Param("tac") Long tac);


	// Query #6
	@Query(value = "SELECT imsi, COUNT(*) AS num_failures, SUM(duration) AS total_duration " + "FROM call_failure "
			+ "WHERE date_time >= :startDate AND date_time <= :endDate " + "GROUP BY IMSI", nativeQuery = true)
	List<Object[]> findAllImsiFailureCountAndDuration(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
