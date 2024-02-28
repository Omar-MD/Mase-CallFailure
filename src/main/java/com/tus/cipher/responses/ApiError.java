package com.tus.cipher.responses;

public class ApiError {
	private String errorMsg;
	private String details;

	private ApiError() {}
	public static ApiErrorBuilder builder() {
		return new ApiErrorBuilder();
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public String getDetails() {
		return details;
	}

	// Builder class for ApiError
	public static class ApiErrorBuilder {
		private String errorMsg;
		private String details;

		public ApiErrorBuilder errorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
			return this;
		}

		public ApiErrorBuilder details(String details) {
			this.details = details;
			return this;
		}

		public ApiError build() {
			ApiError error = new ApiError();
			error.errorMsg = errorMsg;
			error.details = details;
			return error;
		}
	}

	// Static method for creating a new instance of ApiError directly
	public static ApiError of(String errorMsg, String details) {
		return ApiError.builder().errorMsg(errorMsg).details(details).build();
	}
}
