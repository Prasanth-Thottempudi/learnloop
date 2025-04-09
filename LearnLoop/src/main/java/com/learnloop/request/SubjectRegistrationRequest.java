package com.learnloop.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SubjectRegistrationRequest {

    @NotBlank(message = "Subject name is required")
    @Size(max = 50, message = "Subject name must be at most 50 characters")
    private String subjectName;

    @NotNull(message = "Subject credits are required")
    @Min(value = 1, message = "Credits must be at least 1")
    @Max(value = 99, message = "Credits must be less than 100")
    private Integer subjectCredits;

}
