package com.learnloop.controller;

import com.learnloop.request.SubjectRegistrationRequest;
import com.learnloop.response.SubjectResponse;
import com.learnloop.service.SubjectService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectsController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/register")
    public ResponseEntity<SubjectResponse> registerSubject(@Valid @RequestBody SubjectRegistrationRequest request) {
        SubjectResponse subject = subjectService.registerSubject(request);
        return ResponseEntity.status(201).body(subject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponse> getSubjectById(@PathVariable Integer id) {
        return ResponseEntity.ok(subjectService.getSubjectById(id));
    }

    @GetMapping("/get-all-subjects")
    public ResponseEntity<List<SubjectResponse>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @PutMapping("/update-subject/{id}")
    public ResponseEntity<SubjectResponse> updateSubject(
            @PathVariable Integer id,
            @Valid @RequestBody SubjectRegistrationRequest request) {
        return ResponseEntity.ok(subjectService.updateSubject(id, request));
    }

    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable Integer id) {
        subjectService.deleteSubject(id);
        return ResponseEntity.ok("Subject deleted successfully");
    }
}
