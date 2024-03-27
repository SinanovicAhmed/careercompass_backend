package com.careercompass.careercompass.exception;

import lombok.Data;

@Data
public class DefaultErrorResponse {
    private String timestamp;
    private String message;
    private Integer status;
}
