package com.learnloop.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorResponse {

	 private LocalDateTime timestamp;
	    private int status;
	    private String error;
	    private String path;
}
