package com.learnloop.response;

import lombok.Data;

@Data
public class AddressResponse {

	private Integer addId;
	private String street;
	private String city;
	private String state;
	private String zipCode;
	private String country;
}
