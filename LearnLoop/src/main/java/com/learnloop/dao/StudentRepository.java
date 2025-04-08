package com.learnloop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnloop.entity.Students;

public interface StudentRepository extends JpaRepository<Students, Integer> {
    boolean existsByStudentAdharNumber(String studentAdharNumber);
    boolean existsByMobileNumber(String studentMobileNumber);
}
