package com.learnloop.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnloop.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{

}
