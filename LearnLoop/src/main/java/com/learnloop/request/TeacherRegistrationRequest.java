package com.learnloop.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class TeacherRegistrationRequest {

    @NotBlank(message = "Teacher name is required")
    @Size(max = 100, message = "Teacher name must be at most 100 characters")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Teacher name must contain only letters and spaces")
    private String teacherName;

    @NotBlank(message = "Date of birth is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date of birth must be in YYYY-MM-DD format")
    private String teacherDateOfBirth;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Mobile number must be a valid 10-digit Indian number starting with 6-9")
    private String teacherMobileNumber;

    @Valid
    @NotNull(message = "Address is required")
    private AddressRequest address;

    @Valid
    private List<ExperienceRequest> experiences;

    @Valid
    private List<EducationRequest> educations;
}
