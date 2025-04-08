package com.learnloop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learnloop.entity.Students;
import com.learnloop.request.StudentRegistrationRequest;
import com.learnloop.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
public class StudentsController {
	
	@Autowired
	private StudentService studentService;

    @GetMapping("/demo")
    public String demo() {
        return "students demo";
    }
    
   
    @PostMapping("/register")
    public ResponseEntity<?> registerStudent(@Valid @RequestBody StudentRegistrationRequest request) {
        Students student = studentService.registerStudent(request);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }


 
    
}

