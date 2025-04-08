package com.learnloop.response;

import lombok.Data;

@Data
public class StudentResponse {
	
	private Integer studentId;
	private String studentName;
	private String studentMobileNumber;
	private String studentDateOfBirth;
	private String studentAdharNumber;
	private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
