package com.learnloop.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EducationRequest {


    @NotBlank(message = "Degree name is required")
    @Size(max = 100)
    private String degreeName;

    @NotBlank(message = "Institution name is required")
    @Size(max = 100)
    private String institutionName;

    @NotBlank(message = "Field of study is required")
    @Size(max = 100)
    private String fieldOfStudy;

    @NotNull(message = "Start year is required")
    @Min(1900)
    private Integer startYear;

    @NotNull(message = "End year is required")
    @Min(1900)
    private Integer endYear;

    @NotBlank(message = "Grade is required")
    @Size(max = 20)
    private String grade;
}
