package com.tus.cipher.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
	private int statusCode;
	private String status;
	private T data;
	private ApiError error;

	private ApiResponse() {
	}

	public static <T> ApiResponseBuilder<T> builder() {
		return new ApiResponseBuilder<>();
	}

	// Getter methods
	public int getStatusCode() {
		return statusCode;
	}

	public String getStatus() {
		return status;
	}

	public T getData() {
		return data;
	}

	public ApiError getError() {
		return error;
	}

	// Builder class for ApiResponse
	public static class ApiResponseBuilder<T> {
		private int statusCode;
		private String status;
		private T data;
		private ApiError error;

		public ApiResponseBuilder<T> statusCode(int statusCode) {
			this.statusCode = statusCode;
			return this;
		}

		public ApiResponseBuilder<T> status(String status) {
			this.status = status;
			return this;
		}

		public ApiResponseBuilder<T> data(T data) {
			this.data = data;
			return this;
		}

		public ApiResponseBuilder<T> error(ApiError error) {
			this.error = error;
			return this;
		}

		public ApiResponse<T> build() {
			ApiResponse<T> response = new ApiResponse<>();
			response.statusCode = statusCode;
			response.status = status;
			response.data = data;
			if (error != null) {
				response.error = error;
			}
			return response;
		}
	}

	public static <T> ApiResponse<T> success(int statusCode, T data) {
		return ApiResponse.<T>builder().statusCode(statusCode).status("Success").data(data).build();
	}

	public static <T> ApiResponse<T> error(int statusCode, ApiError error) {
		return ApiResponse.<T>builder().statusCode(statusCode).status("Error").error(error).build();
	}
}
