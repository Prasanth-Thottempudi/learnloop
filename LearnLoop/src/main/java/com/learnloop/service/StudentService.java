package com.learnloop.service;

import com.learnloop.entity.Students;
import com.learnloop.request.StudentRegistrationRequest;

public interface StudentService {

	public Students registerStudent(StudentRegistrationRequest request);
}
