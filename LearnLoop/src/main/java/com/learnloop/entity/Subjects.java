package com.learnloop.entity;


import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "subjects_data")
@Data
public class Subjects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="subject_id")
    private Integer subId;

    @NotBlank(message = "Subject name is mandatory")
    @Size(max = 50, message = "Subject name can be at most 50 characters")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Subject name must contain only letters and spaces")
    @Column(name="subject_name")
    private String subName;

    @NotNull(message = "Subject credits are mandatory")
    @Min(value = 1, message = "Credits must be at least 1")
    @Max(value = 99, message = "Credits should not exceed 2 digits")
    @Column(name="subject_credits")
    private Integer subCredits;

    @NotBlank(message = "Subject code is mandatory")
    @Size(min = 4, max = 10, message = "Subject code must be between 4 and 10 characters")
    @Pattern(regexp = "^[A-Z0-9]+$", message = "Subject code must be uppercase alphanumeric")
    @Column(name="subject_code")
    private String subCode;

    @ManyToMany(mappedBy = "subjects")
    private Set<Students> students = new HashSet<>();
}
