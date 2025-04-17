package com.learnloop.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learnloop.dao.SubjectsRepository;
import com.learnloop.entity.Subjects;
import com.learnloop.exceptions.ResourceNotFoundException;
import com.learnloop.request.SubjectRegistrationRequest;
import com.learnloop.response.Response;
import com.learnloop.response.SubjectResponse;
import com.learnloop.service.SubjectService;

import jakarta.transaction.Transactional;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectsRepository subjectsRepository;

    @Override
    public SubjectResponse registerSubject(SubjectRegistrationRequest request) {
        Subjects subject = new Subjects();
        subject.setSubName(request.getSubjectName());
        subject.setSubCredits(request.getSubjectCredits());
        subject.setSubCode(generateCode(request.getSubjectName()));
        return mapToResponse(subjectsRepository.save(subject));
    }

    @Override
    public SubjectResponse getSubjectById(Integer id) {
        Subjects subject = subjectsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));
        return mapToResponse(subject);
    }

    @Override
    public List<SubjectResponse> getAllSubjects() {
        return subjectsRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SubjectResponse updateSubject(Integer id, SubjectRegistrationRequest request) {
        Subjects subject = subjectsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with the given id"+id));
        subject.setSubName(request.getSubjectName());
        subject.setSubCredits(request.getSubjectCredits());
        subject.setSubCode(generateCode(request.getSubjectName()));
        return mapToResponse(subjectsRepository.save(subject));
    }

    @Override
    @Transactional
    public Response deleteSubject(Integer id) {
        Subjects subject = subjectsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with ID: " + id));

        subjectsRepository.delete(subject);

        Response response = new Response();
        response.setResponseMessage("Subject with ID " + id + " deleted successfully.");
        response.setResponseStatus("Success");
        response.setId(id);

        return response;
    }


    private SubjectResponse mapToResponse(Subjects subject) {
        SubjectResponse response = new SubjectResponse();
        response.setSubjectId(subject.getSubId());
        response.setSubjectName(subject.getSubName());
        response.setSubjectCredits(subject.getSubCredits());
        response.setSubjectCode(subject.getSubCode());
        return response;
    }

    private String generateCode(String name) {
        String prefix = name.replaceAll("\\s+", "").toUpperCase().substring(0, Math.min(4, name.length()));
        return prefix + (int) (Math.random() * 900 + 100);
    }
}
