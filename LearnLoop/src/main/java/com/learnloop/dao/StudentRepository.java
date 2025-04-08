package com.learnloop.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnloop.entity.Students;

public interface StudentRepository extends JpaRepository<Students, Integer> {
    boolean existsByStudentAdharNumber(String studentAdharNumber);
    boolean existsByMobileNumber(String studentMobileNumber);
	Optional<Students> findById(Long id);
}
