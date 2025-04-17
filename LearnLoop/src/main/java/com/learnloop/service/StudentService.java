package com.learnloop.service;

import java.util.List;

import com.learnloop.entity.Students;
import com.learnloop.request.StudentRegistrationRequest;
import com.learnloop.response.Response;
import com.learnloop.response.StudentResponse;

import jakarta.validation.Valid;

public interface StudentService {

	public StudentResponse registerStudent(StudentRegistrationRequest request);

	public StudentResponse getStudentById(Long id);

	public List<StudentResponse> getAllStudents();

	public StudentResponse updateStudent(Long id, @Valid StudentRegistrationRequest request);

	public Response deleteStudent(Long id);
}
