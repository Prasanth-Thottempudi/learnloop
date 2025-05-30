package com.learnloop.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "experience_data")
@Data
@ToString(onlyExplicitlyIncluded = true)
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exp_id")
    private Integer expId;

    @NotBlank(message = "Institution name is required")
    @Size(max = 100, message = "Institution name must be at most 100 characters")
    @Column(name = "institution_name", nullable = false)
    private String institutionName;

    @NotNull(message = "Start date is required")
//    @PastOrPresent(message = "Start date must be in the past or present")
    @Column(name = "institution_start_date", nullable = false)
    private LocalDate institutionStartDate;

    @NotNull(message = "End date is required")
//    @PastOrPresent(message = "End date must be in the past or present")
    @Column(name = "institution_end_date", nullable = false)
    private LocalDate institutionEndDate;

    @NotBlank(message = "Designation is required")
    @Size(max = 50, message = "Designation must be at most 50 characters")
    @Column(name = "designation", nullable = false)
    private String designation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;
}
