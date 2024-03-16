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

	/*
	 *  CUSTOMER SERVICE REP QUERIES
	 */

	// Query #1
	@Query(value = "SELECT cf.cause_code, cf.event_id, ec.description " + "FROM call_failure cf "
			+ "INNER JOIN event_cause ec ON cf.cause_code = ec.cause_code AND cf.event_id = ec.event_id "
			+ "WHERE cf.imsi = :imsi", nativeQuery = true)
	List<Object[]> findImsiEventCauseDescriptions(@Param("imsi") Long imsi);

	// Query #2
    @Query("SELECT COUNT(c) FROM CallFailure c WHERE c.imsi = :imsi AND c.dateTime BETWEEN :startDate AND :endDate")
    long countByImsiAndDateTimeBetween(@Param("imsi") Long imsi, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate );

	//Query #8
	@Query(value = "SELECT DISTINCT cf.cause_code, cf.event_id, ec.description " + "FROM call_failure cf "
			+ "INNER JOIN event_cause ec ON cf.cause_code = ec.cause_code AND cf.event_id = ec.event_id "
			+ "WHERE cf.imsi = :imsi", nativeQuery = true)
	List<Object[]> findImsiUniqueEventCauseDescriptions(@Param("imsi") Long imsi);

	/*
	 *  SUPPORT ENGINEER QUERIES
	 */

    //Query #3
	@Query("SELECT DISTINCT c.imsi FROM CallFailure c WHERE c.dateTime BETWEEN :startDate AND :endDate")
	List<Long> findDistinctImsiByDateTimeBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

	// Query #4
	@Query(value = "SELECT COUNT (cf.tac) FROM call_failure cf WHERE cf.tac = :tac and cf.date_time BETWEEN :startDate AND :endDate", nativeQuery = true)
	Long getModelFaliureCount(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("tac") Long tac );

	// query 4.5
	@Query(value = "SELECT DISTINCT (cf.imsi) FROM call_failure cf WHERE cf.failure_code = :failureClass", nativeQuery = true)
	List<Long> getIMSIsWithFailureClass(@Param("failureClass") Long failureClass);

	/*
	 *  NETWORK ENGINEER QUERIES
	 */

	// Query #5
	@Query(value = "SELECT cf.cause_code, cf.event_id, COUNT(*) as failure_count " + "FROM call_failure cf "
			+ "INNER JOIN event_cause ec ON cf.cause_code = ec.cause_code AND cf.event_id = ec.event_id "
			+ "WHERE cf.tac = :tac " + "GROUP BY cf.cause_code, cf.event_id", nativeQuery = true)
	List<Object[]> findModelsFailureTypesWithCount(@Param("tac") Long tac);

	// Query #6
	@Query(value = "SELECT imsi, COUNT(*) AS num_failures, SUM(duration) AS total_duration " + "FROM call_failure "
			+ "WHERE date_time >= :startDate AND date_time <= :endDate " + "GROUP BY IMSI", nativeQuery = true)
	List<Object[]> findAllImsiFailureCountAndDuration(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

	// Query #7
	@Query(value= "SELECT DISTINCT cf.mcc, cf.mnc, cf.cell_id, COUNT(*) as failure_count " + "FROM call_failure cf "
			+ "WHERE cf.date_time BETWEEN :startDate AND :endDate GROUP BY cf.mcc, cf.mnc, cf.cell_id "
			+ "ORDER BY failure_count DESC LIMIT 10", nativeQuery = true)
	List<Object[]> top10MarketOperatorCellIdCombinations(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

	//Query#9
	@Query(value="SELECT c.imsi, COUNT(*) AS failureCount FROM call_failure c  WHERE c.date_time >= :startDate AND c.date_time <= :endDate GROUP BY c.imsi ORDER BY failureCount DESC LIMIT 10", nativeQuery = true)
    List<Object[]> findTop10IMSIWithFailures(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

	/*
	 *  Drill Down Queries
	 */

    // Failure Over Time for Event ID, Cause Code
	@Query(value="SELECT DATE(cf.date_time) AS date, COUNT(*) As totalFailures FROM call_failure cf  WHERE cf.event_id=:eventId AND cf.cause_code=:causeCode GROUP BY date", nativeQuery = true)
    List<Object[]> findEventCauseFailuresOverTime(@Param("eventId") Long eventId, @Param("causeCode") Long causeCode);

    // Failure Causes & Counts By Cell ID
    @Query(value="SELECT fc.description AS failure_cause, COUNT(*) AS failure_count FROM call_failure cf "
    + "JOIN failure_class fc ON cf.failure_code = fc.failure_code WHERE cf.cell_id = :cellId GROUP BY fc.description", nativeQuery = true)
    List<Object[]> listFailureCausesCountsByCellId(@Param("cellId") Integer cellId);

    // Imsi Failures by Count
    @Query(value="SELECT fc.description AS failure_class, COUNT(*) AS failure_count FROM call_failure cf "
    + "JOIN failure_class fc ON cf.failure_code = fc.failure_code WHERE cf.imsi = :imsi GROUP BY fc.description", nativeQuery = true)
    List<Object[]> listImsiFailuresByClass(@Param("imsi") long imsi);

    // Imsi Failures Class Event Cause
    @Query(value="SELECT CONCAT(cf.event_id, '_', cf.cause_code) AS eventCause, COUNT(*) AS failure_count FROM call_failure cf "
    + "JOIN failure_class fc ON cf.failure_code = fc.failure_code WHERE cf.imsi = :imsi AND fc.description =:failureClass GROUP BY eventCause", nativeQuery = true)
    List<Object[]> listImsiFailuresClassEventCause(@Param("imsi") long imsi, @Param("failureClass") String failureClass);
}


