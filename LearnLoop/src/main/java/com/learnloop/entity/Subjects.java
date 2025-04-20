package com.learnloop.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "subjects_data", uniqueConstraints = {
    @UniqueConstraint(columnNames = "subject_code", name = "UK_subject_code")
})
@Data
public class Subjects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Integer subId;

    @NotBlank(message = "Subject name is mandatory")
    @Size(min = 3, max = 50, message = "Subject name must be between 3 and 50 characters")
    @Pattern(
        regexp = "^(?!\\s)(?!.*\\s{2,})(?!.*\\s$)[A-Za-z][A-Za-z\\s]*$",
        message = "Subject name must contain only letters, single spaces, and no leading/trailing/multiple spaces"
    )
    @Column(name = "subject_name", nullable = false)
    private String subName;

    @NotNull(message = "Subject credits are mandatory")
    @Min(value = 1, message = "Credits must be at least 1")
    @Max(value = 20, message = "Credits must not exceed 20")
    @Column(name = "subject_credits", nullable = false)
    private Integer subCredits;

    @NotBlank(message = "Subject code is mandatory")
    @Size(min = 4, max = 10, message = "Subject code must be between 4 and 10 characters")
    @Pattern(
        regexp = "^[A-Z]{2,}[0-9]{2,}$",
        message = "Subject code must start with at least 2 uppercase letters followed by at least 2 digits"
    )
    @Column(name = "subject_code", nullable = false, unique = true)
    private String subCode;

    @ManyToMany(mappedBy = "subjects")
    @JsonManagedReference
    private Set<Students> students = new HashSet<>();

    @ManyToMany(mappedBy = "subjects")
    @JsonManagedReference
    private Set<Teacher> teachers = new HashSet<>();
}
