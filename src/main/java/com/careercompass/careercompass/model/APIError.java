package com.careercompass.careercompass.model;

import lombok.Data;

@Data
public class APIError {
    private String timestamp;
    private String error;
    private Integer status;
}
