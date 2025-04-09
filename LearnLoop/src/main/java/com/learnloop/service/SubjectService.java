package com.learnloop.service;

import com.learnloop.request.SubjectRegistrationRequest;
import com.learnloop.response.SubjectResponse;

import java.util.List;

public interface SubjectService {
    SubjectResponse registerSubject(SubjectRegistrationRequest request);
    SubjectResponse getSubjectById(Integer id);
    List<SubjectResponse> getAllSubjects();
    SubjectResponse updateSubject(Integer id, SubjectRegistrationRequest request);
    void deleteSubject(Integer id);
}
