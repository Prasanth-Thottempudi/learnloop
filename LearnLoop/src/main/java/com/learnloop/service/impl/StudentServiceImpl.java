package com.learnloop.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learnloop.dao.StudentRepository;
import com.learnloop.dao.SubjectsRepository;
import com.learnloop.entity.Address;
import com.learnloop.entity.Students;
import com.learnloop.entity.Subjects;
import com.learnloop.exceptions.DuplicateStudentException;
import com.learnloop.exceptions.ResourceNotFoundException;
import com.learnloop.request.StudentRegistrationRequest;
import com.learnloop.response.Response;
import com.learnloop.response.StudentResponse;
import com.learnloop.response.StudentResponse.SubjectResponse;
import com.learnloop.service.StudentService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private SubjectsRepository subjectsRepository;
	
	

	@Override
	@Transactional(rollbackOn = Exception.class)
	public StudentResponse registerStudent(StudentRegistrationRequest request) {
	    if (studentRepository.existsByStudentAdharNumber(request.getStudentAdharNumber())) {
	        throw new DuplicateStudentException("A student with this Aadhar already exists.");
	    }

	    if (studentRepository.existsByMobileNumber(request.getStudentMobileNumber())) {
	        throw new DuplicateStudentException("A student with this mobile number already exists.");
	    }

	    // Create Address
	    Address address = new Address();
	    address.setStreet(request.getStreet());
	    address.setCity(request.getCity());
	    address.setState(request.getState());
	    address.setPostalCode(request.getPostalCode());
	    address.setCountry(request.getCountry());

	    // Create Student
	    Students student = new Students();
	    student.setStudentName(request.getStudentName());
	    student.setStudentAdharNumber(request.getStudentAdharNumber());
	    student.setDateOfBirth(request.getStudentDateOfBirth());
	    student.setMobileNumber(request.getStudentMobileNumber());
	    student.setAddress(address);

	    // Assign Subjects
	    List<Subjects> subjects = subjectsRepository.findAllById(request.getSubjectIds());

	    if (subjects.size() != request.getSubjectIds().size()) {
	        throw new IllegalArgumentException("One or more subject IDs are invalid.");
	    }

	    List<Subjects> subjectsSet = new ArrayList<>(subjects);
	    student.setSubjects(subjectsSet);

	    // Save Student
	    Students savedStudent = studentRepository.save(student);

	    // Convert to StudentResponse
	    return mapToStudentResponse(savedStudent);
	}

	
	private StudentResponse mapToStudentResponse(Students student) {
	    StudentResponse response = new StudentResponse();
	    response.setStudentId(student.getId());
	    response.setStudentName(student.getStudentName());
	    response.setStudentAdharNumber(student.getStudentAdharNumber());
	    response.setStudentDateOfBirth(student.getDateOfBirth());
	    response.setStudentMobileNumber(student.getMobileNumber());

	    Address address = student.getAddress();
	    if (address != null) {
	        response.setStreet(address.getStreet());
	        response.setCity(address.getCity());
	        response.setState(address.getState());
	        response.setPostalCode(address.getPostalCode());
	        response.setCountry(address.getCountry());
	    }

	    List<StudentResponse.SubjectResponse> subjectResponses = student.getSubjects()
	        .stream()
	        .map(subject -> {
	            StudentResponse.SubjectResponse subjectResponse = new StudentResponse.SubjectResponse();
	            subjectResponse.setSubjectId(subject.getSubId());
	            subjectResponse.setSubjectName(subject.getSubName());
	            subjectResponse.setSubjectCredits(subject.getSubCredits());
	            subjectResponse.setSubjectCode(subject.getSubCode());
	            return subjectResponse;
	        })
	        .toList();

	    response.setSubjects(subjectResponses);
	    return response;
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
	    List<StudentResponse> response = new ArrayList<>();

	    for (Students subj : students) {
	        StudentResponse stuRes = new StudentResponse();
	        stuRes.setStudentId(subj.getId());
	        stuRes.setStudentName(subj.getStudentName());
	        stuRes.setStudentAdharNumber(subj.getStudentAdharNumber());
	        stuRes.setStudentDateOfBirth(subj.getDateOfBirth());
	        stuRes.setStudentMobileNumber(subj.getMobileNumber());

	        // Map Address
	        if (subj.getAddress() != null) {
	            stuRes.setStreet(subj.getAddress().getStreet());
	            stuRes.setCity(subj.getAddress().getCity());
	            stuRes.setState(subj.getAddress().getState());
	            stuRes.setPostalCode(subj.getAddress().getPostalCode());
	            stuRes.setCountry(subj.getAddress().getCountry());
	        }

	        List<SubjectResponse> subjectResponses = new ArrayList<>();
	        if (subj.getSubjects() != null) {
	            List<Subjects> subjectsCopy = new ArrayList<>(subj.getSubjects());
	            for (Subjects subject : subjectsCopy) {
	                if (subject != null) {
	                    SubjectResponse subjectResponse = new SubjectResponse();
	                    subjectResponse.setSubjectId(subject.getSubId());
	                    subjectResponse.setSubjectName(subject.getSubName());
	                    subjectResponse.setSubjectCredits(subject.getSubCredits());
	                    subjectResponse.setSubjectCode(subject.getSubCode());
	                    subjectResponses.add(subjectResponse);
	                }
	            }
	        }

	        stuRes.setSubjects(subjectResponses);
	        response.add(stuRes);
	    }

	    return response;
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
	public Response deleteStudent(Long id) {
	    Students student = studentRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
	    
	    studentRepository.delete(student);
	    Response response = new Response();
        response.setResponseMessage("Subject with ID " + id + " deleted successfully.");
        response.setResponseStatus("Success");
        response.setId(student.getId());

        return response;
	}


}
