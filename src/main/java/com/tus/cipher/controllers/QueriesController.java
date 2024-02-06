package com.tus.cipher.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tus.cipher.dao.CallFailureDAO;
import com.tus.cipher.exceptions.ApiError;
import com.tus.cipher.exceptions.ApiResponse;

@RestController
@RequestMapping("/query")
public class QueriesController {

	private final CallFailureDAO callFailureDAO;

	public QueriesController(CallFailureDAO callFailureDAO) {
		this.callFailureDAO = callFailureDAO;
	}

	@GetMapping("/imsi-failures/{imsi}")
	public ApiResponse<Object> findImsiFailures(@PathVariable("imsi") long imsi) {
		List<Long> listValidImsi = callFailureDAO.listImsi();

		if(listValidImsi.contains(imsi)) {
			List<Object[]> imsiEventCauseDescriptions = callFailureDAO.findImsiEventCauseDescriptions(imsi);

			List<Map<String, Object>> responseList = new ArrayList<>();
			for (Object[] entry : imsiEventCauseDescriptions) {
				System.out.println(entry);

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
}
