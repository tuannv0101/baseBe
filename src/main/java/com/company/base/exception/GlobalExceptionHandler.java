package com.company.base.exception;

import com.company.base.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Void>> handleAppException(AppException e) {
        return ResponseEntity.status(e.getStatus()).body(ApiResponse.error(e.getStatus(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(500, "Unexpected error: " + e.getMessage()));
    }
}
