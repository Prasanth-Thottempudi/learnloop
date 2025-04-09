package com.learnloop.response;

import lombok.Data;

@Data
public class EducationResponse {

    private Integer educationId;
    private String degreeName;
    private String institutionName;
    private String fieldOfStudy;
    private Integer startYear;
    private Integer endYear;
    private String grade;
}
