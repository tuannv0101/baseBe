package com.company.base.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder().status(200).message("Success").data(data).timestamp(LocalDateTime.now()).build();
    }
    public static <T> ApiResponse<T> error(int status, String message) {
        return ApiResponse.<T>builder().status(status).message(message).timestamp(LocalDateTime.now()).build();
    }
}
