package com.learnloop.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "teacher_data")
@Data
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teacherId;

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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    @NotNull(message = "Address is required")
    private Address address;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Experience> experiences = new ArrayList<>();
    
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Education> educations = new ArrayList<>();
    
    
    @ManyToMany
    @JoinTable(
        name = "teacher_subjects",
        joinColumns = @JoinColumn(name = "teacher_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subjects> subjects = new ArrayList<>();
    
}
