package com.learnloop.service;

import java.util.List;

import com.learnloop.request.TeacherRegistrationRequest;
import com.learnloop.response.TeacherResponse;

import jakarta.validation.Valid;

public interface TeacherService {

	TeacherResponse registerTeacher(@Valid TeacherRegistrationRequest request);

	TeacherResponse getTeacherById(Integer id);

	List<TeacherResponse> getAllTeachers();

	TeacherResponse updateTeacher(Integer id, @Valid TeacherRegistrationRequest request);

	String deleteTeacher(Integer id);

}
