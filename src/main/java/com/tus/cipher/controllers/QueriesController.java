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
import com.tus.cipher.responses.ApiError;
import com.tus.cipher.responses.ApiResponse;

@RestController
@RequestMapping("/query")
public class QueriesController {

	private final CallFailureDAO callFailureDAO;

	public QueriesController(CallFailureDAO callFailureDAO) {
		this.callFailureDAO = callFailureDAO;
	}

	/*	 IMSI Failures	 */

	@GetMapping("/imsi-failures")
	public ApiResponse<Object> getImsiFailures() {
		List<Long> listValidImsi = callFailureDAO.listImsi();
		return ApiResponse.success(HttpStatus.OK.value(), listValidImsi);
	}

	@GetMapping("/imsi-failures/{imsi}")
	public ApiResponse<Object> findImsiFailures(@PathVariable("imsi") long imsi) {
		List<Long> listValidImsi = callFailureDAO.listImsi();

		if(listValidImsi.contains(imsi)) {
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

	@GetMapping("/imsi-failures-time")
	public ApiResponse<Object> findImsiFailures(
					@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startDate,
					@RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endDate) {
		List<Long> distinctImsiList = callFailureDAO.findDistinctImsiByDateTimeBetween(startDate, endDate);
		return ApiResponse.success(HttpStatus.OK.value(), distinctImsiList);
	}


	/* Model Failures */

	@GetMapping("/model-failures")
	public ApiResponse<Object> getModelsWithFailure() {
		List<Long> listValidTac = callFailureDAO.listTac();
		return ApiResponse.success(HttpStatus.OK.value(), listValidTac);
	}

	@GetMapping("/model-failures/{tac}")
	public ApiResponse<Object> findModelsFailureTypesWithCount(@PathVariable("tac") long tac) {
		List<Long> listValidTac = callFailureDAO.listTac();

		if(listValidTac.contains(tac)) {
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
}
