package com.learnloop.response;


import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String path;
    private Map<String, String> fieldErrors; // For validation errors
}
