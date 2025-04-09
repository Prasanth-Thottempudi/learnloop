package com.learnloop.response;

import java.util.List;

import lombok.Data;

@Data
public class TeacherResponse {
    private Integer teacherId;
    private String teacherName;
    private String teacherDateOfBirth;
    private String teacherMobileNumber;
    private AddressResponse address;
    private List<ExperienceResponse> experiences;
    private List<EducationResponse> educations;
}
