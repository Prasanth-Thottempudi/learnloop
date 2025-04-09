package com.learnloop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "education_data")
@Data
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_id")
    private Integer educationId;

    @NotBlank(message = "Degree name is required")
    @Size(max = 100, message = "Degree name must be at most 100 characters")
    @Column(name = "degree_name", nullable = false)
    private String degreeName;

    @NotBlank(message = "Institution name is required")
    @Size(max = 100, message = "Institution name must be at most 100 characters")
    @Column(name = "institution_name", nullable = false)
    private String institutionName;

    @NotBlank(message = "Field of study is required")
    @Size(max = 100, message = "Field of study must be at most 100 characters")
    @Column(name = "field_of_study", nullable = false)
    private String fieldOfStudy;

    @NotNull(message = "Start year is required")
    @Min(value = 1900, message = "Start year must be a valid year")
    @Column(name = "start_year", nullable = false)
    private Integer startYear;

    @NotNull(message = "End year is required")
    @Min(value = 1900, message = "End year must be a valid year")
    @Column(name = "end_year", nullable = false)
    private Integer endYear;

    @NotBlank(message = "Grade or percentage is required")
    @Size(max = 20, message = "Grade must be at most 20 characters")
    @Column(name = "grade", nullable = false)
    private String grade;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;
}
