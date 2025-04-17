package com.learnloop.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "students_data")
@Data
public class Students {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	

    @Column(name = "student_name", nullable = false, length = 100)
    @NotBlank(message = "Student name is mandatory")
    @Size(max = 20, message = "Student name can be at most 20 characters")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "Student name must contain only letters and spaces")
    private String studentName;

    @Column(name = "student_adhar_number", nullable = false, unique = true, length = 12)
    @NotBlank(message = "Aadhar number is mandatory")
    @Pattern(regexp = "^[0-9]{12}$", message = "Aadhar number must be 12 digits")
    private String studentAdharNumber;

    @Column(name = "student_date_of_birth", nullable = false, length = 10)
    @NotBlank(message = "Date of birth is mandatory")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date of birth must be in YYYY-MM-DD format")
    private String dateOfBirth;

    @Column(name = "student_mobile_number", nullable = false, unique = true, length = 10)
    @NotBlank(message = "Mobile number is mandatory")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Mobile number must start with 6-9 and be 10 digits")
    private String mobileNumber;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;
    
    @ManyToMany
    @JoinTable(
        name = "student_subjects",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subjects> subjects = new ArrayList<>();
}
