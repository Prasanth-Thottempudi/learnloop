package com.learnloop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learnloop.dao.StudentRepository;
import com.learnloop.entity.Address;
import com.learnloop.entity.Students;
import com.learnloop.exceptions.DuplicateStudentException;
import com.learnloop.exceptions.ResourceNotFoundException;
import com.learnloop.request.StudentRegistrationRequest;
import com.learnloop.response.StudentResponse;
import com.learnloop.service.StudentService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	

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

	@Override
    public StudentResponse getStudentById(Long id) {
		StudentResponse res=new StudentResponse();
        Students student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        res.setStudentName(student.getStudentName());
        res.setStudentMobileNumber(student.getMobileNumber());
        res.setStudentId(student.getId());
        res.setStudentDateOfBirth(student.getDateOfBirth());
        res.setStudentAdharNumber(student.getStudentAdharNumber());
        res.setStreet(student.getAddress().getStreet());
        res.setState(student.getAddress().getState());
        res.setPostalCode(student.getAddress().getPostalCode());
        res.setCountry(student.getAddress().getCountry());
        res.setCity(student.getAddress().getCity());
        return res;
    }

	@Override
	public List<StudentResponse> getAllStudents() {
	    List<Students> students = studentRepository.findAll();

	    return students.stream().map(student -> {
	        StudentResponse response = new StudentResponse();
	        response.setStudentId(student.getId());
	        response.setStudentName(student.getStudentName());
	        response.setStudentAdharNumber(student.getStudentAdharNumber());
	        response.setStudentDateOfBirth(student.getDateOfBirth());
	        response.setStudentMobileNumber(student.getMobileNumber());

	        if (student.getAddress() != null) {
	            response.setStreet(student.getAddress().getStreet());
	            response.setCity(student.getAddress().getCity());
	            response.setState(student.getAddress().getState());
	            response.setPostalCode(student.getAddress().getPostalCode());
	            response.setCountry(student.getAddress().getCountry());
	        }

	        return response;
	    }).toList();
	}


	@Override
	public StudentResponse updateStudent(Long id, @Valid StudentRegistrationRequest request) {
	    Students existingStudent = studentRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));

	    if (!existingStudent.getStudentAdharNumber().equals(request.getStudentAdharNumber()) &&
	        studentRepository.existsByStudentAdharNumber(request.getStudentAdharNumber())) {
	        throw new DuplicateStudentException("A student with this Aadhar already exists.");
	    }

	    if (!existingStudent.getMobileNumber().equals(request.getStudentMobileNumber()) &&
	        studentRepository.existsByMobileNumber(request.getStudentMobileNumber())) {
	        throw new DuplicateStudentException("A student with this mobile number already exists.");
	    }

	    // Update student details
	    existingStudent.setStudentName(request.getStudentName());
	    existingStudent.setStudentAdharNumber(request.getStudentAdharNumber());
	    existingStudent.setDateOfBirth(request.getStudentDateOfBirth());
	    existingStudent.setMobileNumber(request.getStudentMobileNumber());

	    Address address = existingStudent.getAddress();
	    if (address == null) {
	        address = new Address();
	    }
	    address.setStreet(request.getStreet());
	    address.setCity(request.getCity());
	    address.setState(request.getState());
	    address.setPostalCode(request.getPostalCode());
	    address.setCountry(request.getCountry());
	    existingStudent.setAddress(address);

	    Students updatedStudent = studentRepository.save(existingStudent);

	    // Map to StudentResponse
	    StudentResponse response = new StudentResponse();
	    response.setStudentId(updatedStudent.getId());
	    response.setStudentName(updatedStudent.getStudentName());
	    response.setStudentAdharNumber(updatedStudent.getStudentAdharNumber());
	    response.setStudentDateOfBirth(updatedStudent.getDateOfBirth());
	    response.setStudentMobileNumber(updatedStudent.getMobileNumber());

	    if (updatedStudent.getAddress() != null) {
	        response.setStreet(updatedStudent.getAddress().getStreet());
	        response.setCity(updatedStudent.getAddress().getCity());
	        response.setState(updatedStudent.getAddress().getState());
	        response.setPostalCode(updatedStudent.getAddress().getPostalCode());
	        response.setCountry(updatedStudent.getAddress().getCountry());
	    }

	    return response;
	}

	@Override
	public void deleteStudent(Long id) {
	    Students student = studentRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
	    
	    studentRepository.delete(student);
	}


}
