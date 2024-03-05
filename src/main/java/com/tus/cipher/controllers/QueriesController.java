package com.tus.cipher.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tus.cipher.dao.CallFailureDAO;
import com.tus.cipher.dao.FailureClassDAO;
import com.tus.cipher.dto.sheets.FailureClass;
import com.tus.cipher.responses.ApiError;
import com.tus.cipher.responses.ApiResponse;

@RestController
@RequestMapping("/query")
public class QueriesController {
	private static final String DATE_ERR = "Bad Date Range";
	private static final String DATE_ERR_DETAIL = "End date must be after start date";

	private final CallFailureDAO callFailureDAO;
	private final FailureClassDAO failureClassDAO;

	public QueriesController(CallFailureDAO callFailureDAO, FailureClassDAO failureClassDAO) {
		this.callFailureDAO = callFailureDAO;
		this.failureClassDAO = failureClassDAO;
	}

	@GetMapping("/imsi-failures")
	public ApiResponse<List<Long>> getImsiFailures() {
		List<Long> listValidImsi = callFailureDAO.listImsi();
		return ApiResponse.success(HttpStatus.OK.value(), listValidImsi);
	}

	@GetMapping("/model-failures")
	public ApiResponse<List<Long>> getModelsWithFailure() {
		List<Long> listValidTac = callFailureDAO.listTac();
		return ApiResponse.success(HttpStatus.OK.value(), listValidTac);
	}

	@GetMapping("/failure-cause-classes")
	public ApiResponse<List<FailureClass>> getIMSIFailureForCauseClass() {
		List<FailureClass> listFailureClasses = failureClassDAO.findAll();
		return ApiResponse.success(HttpStatus.OK.value(), listFailureClasses);
	}

	// Query #1
	@GetMapping("/imsi-failures/{imsi}")
	public ApiResponse<Object> findImsiFailures(@PathVariable("imsi") long imsi) {
		List<Long> listValidImsi = callFailureDAO.listImsi();

		if (listValidImsi.contains(imsi)) {
			List<Object[]> imsiEventCauseDescriptions = callFailureDAO.findImsiEventCauseDescriptions(imsi);

			List<Map<String, Object>> responseList = new ArrayList<>();
			for (Object[] entry : imsiEventCauseDescriptions) {
				Map<String, Object> result = new HashMap<>();
				result.put("causeCode", entry[0]);
				result.put("eventId", entry[1]);
				result.put("description", entry[2]);
				responseList.add(result);
			}

			return ApiResponse.success(HttpStatus.OK.value(), responseList);
		}

		ApiError error = ApiError.of("Invalid Imsi", "IMSI not in database");
		return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), error);
	}

	// Query #2
	@GetMapping("/imsi-failure-count-time")
	public ApiResponse<Long> getImsiFailureCountTimeRange(@RequestParam("imsi") Long imsi,
			@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endDate) {
		if (endDate.isBefore(startDate)) {
			ApiError error = ApiError.of(DATE_ERR, DATE_ERR_DETAIL);
			return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), error);
		}
		Long count = callFailureDAO.countByImsiAndDateTimeBetween(imsi, startDate, endDate);
		return ApiResponse.success(HttpStatus.OK.value(), count);
	}

	// Query #3
	@GetMapping("/imsi-failures-time")
	public ApiResponse<Object> findImsiFailures(
			@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endDate) {
		if (endDate.isBefore(startDate)) {
			ApiError error = ApiError.of(DATE_ERR, DATE_ERR_DETAIL);
			return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), error);
		}
		List<Long> distinctImsiList = callFailureDAO.findDistinctImsiByDateTimeBetween(startDate, endDate);
		return ApiResponse.success(HttpStatus.OK.value(), distinctImsiList);
	}

	// Query #4
	@GetMapping("/model-failure-count")
	public ApiResponse<Long> getModelsFaliureCount(
			@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endDate,
			@RequestParam("tac") Long tac) {
		if (endDate.isBefore(startDate)) {
			ApiError error = ApiError.of(DATE_ERR, DATE_ERR_DETAIL);
			return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), error);
		}
		long modelfailurecount = callFailureDAO.getModelFaliureCount(startDate, endDate, tac);
		return ApiResponse.success(HttpStatus.OK.value(), modelfailurecount);
	}

	// Query 4.5
	@GetMapping("/imsi-failures-class/{failureClass}")
	public ApiResponse<List<Long>> getIMSIFailureClasses(
			@PathVariable("failureClass") Long failureClass) {
		List<Long> imsiFailures = callFailureDAO.getIMSIsWithFailureClass(failureClass);
		return ApiResponse.success(HttpStatus.OK.value(), imsiFailures);
	}


	// Query #5
	@GetMapping("/model-failures/{tac}")
	public ApiResponse<Object> findModelsFailureTypesWithCount(@PathVariable("tac") long tac) {
		List<Long> listValidTac = callFailureDAO.listTac();

		if (listValidTac.contains(tac)) {
			List<Object[]> modelsFailureTypesWithCount = callFailureDAO.findModelsFailureTypesWithCount(tac);
			List<Map<String, Object>> responseList = new ArrayList<>();
			for (Object[] entry : modelsFailureTypesWithCount) {
				Map<String, Object> result = new HashMap<>();
				result.put("causeCode", entry[0]);
				result.put("eventId", entry[1]);
				result.put("failureCount", entry[2]);
				responseList.add(result);
			}
			return ApiResponse.success(HttpStatus.OK.value(), responseList);
		}

		ApiError error = ApiError.of("Invalid Tac", "Tac not in database");
		return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), error);
	}

	// Query #6
	@GetMapping("/imsi-failures-count-duration")
	public ApiResponse<Object> getcallFailureCountAndDuration(
			@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endDate) {

		if (endDate.isBefore(startDate)) {
			ApiError error = ApiError.of(DATE_ERR, DATE_ERR_DETAIL);
			return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), error);
		}

		List<Object[]> listImsiFailureCountAndDuration = callFailureDAO.findAllImsiFailureCountAndDuration(startDate,
				endDate);

		List<Map<String, Object>> responseList = new ArrayList<>();
		for (Object[] entry : listImsiFailureCountAndDuration) {
			Map<String, Object> result = new HashMap<>();
			result.put("imsi", entry[0]);
			result.put("failureCount", entry[1]);
			result.put("totalDuration", entry[2]);
			responseList.add(result);
		}
		return ApiResponse.success(HttpStatus.OK.value(), responseList);
	}
	
	//Query#7
	
	@GetMapping("/top10-imsi-failures-time")
	public ApiResponse<Object> findTop10ImsiFailures(
			@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startDate,
			@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endDate) {
		if (endDate.isBefore(startDate)) {
			ApiError error = ApiError.of(DATE_ERR, DATE_ERR_DETAIL);
			return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), error);
		}
		List<Long> top10ImsiList = callFailureDAO.findTop10IMSIWithFailures(startDate, endDate);
		return ApiResponse.success(HttpStatus.OK.value(), top10ImsiList);
	}
}
