package com.careercompass.careercompass.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class InvalidArgumentsErrorResponse extends DefaultErrorResponse {
    private List<String> additionalInfo;

    public InvalidArgumentsErrorResponse(String timestamp, String error, Integer status, List<String> additionalInfo) {
        super.setTimestamp(timestamp);
        super.setMessage(error);
        super.setStatus(status);
        this.additionalInfo = additionalInfo;
    }
}