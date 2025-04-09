package com.learnloop.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExperienceResponse {

    private Integer expId;
    private String institutionName;
    private LocalDate institutionStartDate;
    private LocalDate institutionEndDate;
    private String designation;
}
