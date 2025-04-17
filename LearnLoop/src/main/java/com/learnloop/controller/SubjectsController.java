package com.learnloop.controller;

import com.learnloop.request.SubjectRegistrationRequest;
import com.learnloop.response.Response;
import com.learnloop.response.SubjectResponse;
import com.learnloop.service.SubjectService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/learn-api")
public class SubjectsController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/subjects/register")
    public ResponseEntity<SubjectResponse> registerSubject(@Valid @RequestBody SubjectRegistrationRequest request) {
        SubjectResponse subject = subjectService.registerSubject(request);
        return ResponseEntity.status(201).body(subject);
    }

    @GetMapping("/subjects/{id}")
    public ResponseEntity<SubjectResponse> getSubjectById(@PathVariable Integer id) {
        return ResponseEntity.ok(subjectService.getSubjectById(id));
    }

    @GetMapping("/subjects/get-all-subjects")
    public ResponseEntity<List<SubjectResponse>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @PutMapping("/subjects/update-subject/{id}")
    public ResponseEntity<SubjectResponse> updateSubject(
            @PathVariable Integer id,
            @Valid @RequestBody SubjectRegistrationRequest request) {
        return ResponseEntity.ok(subjectService.updateSubject(id, request));
    }

    @DeleteMapping("/subjects/delete-by-id/{id}")
    public ResponseEntity<Response> deleteSubject(@PathVariable Integer id) {
    	Response res=    subjectService.deleteSubject(id);
        return ResponseEntity.ok(res);
    }
}
