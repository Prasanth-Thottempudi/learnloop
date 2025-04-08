package com.learnloop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learnloop.dao.AddressRepository;
import com.learnloop.dao.StudentRepository;
import com.learnloop.entity.Address;
import com.learnloop.entity.Students;
import com.learnloop.exceptions.DuplicateStudentException;
import com.learnloop.request.StudentRegistrationRequest;
import com.learnloop.service.StudentService;

import jakarta.transaction.Transactional;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private AddressRepository addressRepository;

	@Override
    @Transactional(rollbackOn = Exception.class) 
	public Students registerStudent(StudentRegistrationRequest request) {
		if (studentRepository.existsByStudentAdharNumber(request.getStudentAdharNumber())) {
			throw new DuplicateStudentException("A student with this Aadhar already exists.");
		}

		if (studentRepository.existsByMobileNumber(request.getStudentMobileNumber())) {
			throw new DuplicateStudentException("A student with this mobile number already exists.");
		}

		Address address = new Address();
		address.setStreet(request.getStreet());
		address.setCity(request.getCity());
		address.setState(request.getState());
		address.setPostalCode(request.getPostalCode());
		address.setCountry(request.getCountry());
//		addressRepository.save(address);

		Students student = new Students();
		student.setStudentName(request.getStudentName());
		student.setStudentAdharNumber(request.getStudentAdharNumber());
		student.setDateOfBirth(request.getStudentDateOfBirth());
		student.setMobileNumber(request.getStudentMobileNumber());
		student.setAddress(address);
		return studentRepository.save(student);
	}

}
