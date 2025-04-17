package com.learnloop.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learnloop.request.TeacherRegistrationRequest;
import com.learnloop.response.Response;
import com.learnloop.response.TeacherResponse;
import com.learnloop.service.TeacherService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/learn-api")
@CrossOrigin(origins = "http://localhost:4200") 
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/teachers/register")
    public ResponseEntity<TeacherResponse> registerTeacher(@Valid @RequestBody TeacherRegistrationRequest request) {
    	TeacherResponse res=	teacherService.registerTeacher(request);
    	return ResponseEntity.status(201).body(res);
        
    }

    @GetMapping("/teachers/teacher-by-id/{id}")
    public ResponseEntity<TeacherResponse> getTeacherById(@PathVariable Integer id) {
    	TeacherResponse res=	teacherService.getTeacherById(id);

        return ResponseEntity.status(201).body(res);
    }

    @GetMapping("/teachers/get-all-teachers")
    public ResponseEntity<List<TeacherResponse>> getAllTeachers() {
    	List<TeacherResponse> res=teacherService.getAllTeachers();
        return ResponseEntity.status(201).body(res);
    }

    @PutMapping("/teachers/update-teacher/{id}")
    public ResponseEntity<TeacherResponse> updateTeacher(
            @PathVariable Integer id,
            @Valid @RequestBody TeacherRegistrationRequest request) {
    	TeacherResponse res=teacherService.updateTeacher(id, request);
        return ResponseEntity.status(201).body(res);
    }

    @DeleteMapping("/teachers/delete-by-id/{id}")
    public ResponseEntity<Response> deleteTeacher(@PathVariable Integer id) {
        Response response = teacherService.deleteTeacher(id);
        return ResponseEntity.ok(response);  // This will send the structured response back
    }

}
