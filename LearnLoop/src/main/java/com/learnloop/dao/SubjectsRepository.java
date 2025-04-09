package com.learnloop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnloop.entity.Subjects;

public interface SubjectsRepository extends JpaRepository<Subjects, Integer>{

}
