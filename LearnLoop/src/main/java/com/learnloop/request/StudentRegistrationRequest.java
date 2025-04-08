package com.learnloop.request;

import lombok.Data;

@Data
public class StudentRegistrationRequest {
	
	private String studentName;
	private String studentAdharNumber;
	private String studentDateOfBirth;
	private String studentMobileNumber;
	private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
	

}
