package com.learnloop.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ExperienceRequest {

    @NotBlank(message = "Institution name is required")
    @Size(max = 100)
    private String institutionName;

    @NotNull(message = "Start date is required")
    private String institutionStartDate; // Use string and parse it in service

    @NotNull(message = "End date is required")
    private String institutionEndDate;

    @NotBlank(message = "Designation is required")
    @Size(max = 50)
    private String designation;
}
