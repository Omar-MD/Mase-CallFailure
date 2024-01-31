package com.tus.cipher.exceptions;

public class ApiError {
    private int statusCode;
    private String errorMsg;
    private String details;

    private ApiError() {}
    public static ApiErrorBuilder builder() {
        return new ApiErrorBuilder();
    }

    // Getter methods
    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getDetails() {
        return details;
    }

    // Builder class for ApiError
    public static class ApiErrorBuilder {
        private int statusCode;
        private String errorMsg;
        private String details;

        public ApiErrorBuilder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

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
            error.statusCode = statusCode;
            error.errorMsg = errorMsg;
            error.details = details;
            return error;
        }
    }

    // Static method for creating a new instance of ApiError directly
    public static ApiError of(int statusCode, String errorMsg, String details) {
        return ApiError.builder()
                .statusCode(statusCode)
                .errorMsg(errorMsg)
                .details(details)
                .build();
    }

}
