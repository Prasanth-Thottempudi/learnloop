package com.learnloop.controller;



import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.learnloop.request.TeacherRegistrationRequest;
import com.learnloop.response.TeacherResponse;
import com.learnloop.service.TeacherService;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/register")
    public ResponseEntity<TeacherResponse> registerTeacher(@Valid @RequestBody TeacherRegistrationRequest request) {
    	TeacherResponse res=	teacherService.registerTeacher(request);
    	return ResponseEntity.status(201).body(res);
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponse> getTeacherById(@PathVariable Integer id) {
    	TeacherResponse res=	teacherService.getTeacherById(id);

        return ResponseEntity.status(201).body(res);
    }

    @GetMapping("/get-all-teachers")
    public ResponseEntity<List<TeacherResponse>> getAllTeachers() {
    	List<TeacherResponse> res=teacherService.getAllTeachers();
        return ResponseEntity.status(201).body(res);
    }

    @PutMapping("/update-teacher/{id}")
    public ResponseEntity<TeacherResponse> updateTeacher(
            @PathVariable Integer id,
            @Valid @RequestBody TeacherRegistrationRequest request) {
    	TeacherResponse res=teacherService.updateTeacher(id, request);
        return ResponseEntity.status(201).body(res);
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Integer id) {
    	String res=teacherService.deleteTeacher(id);
        return ResponseEntity.status(201).body(res);
    }
}
