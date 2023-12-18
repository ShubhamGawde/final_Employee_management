package com.employeemanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeemanagement.entity.Allowance;

public interface AllowanceRepo extends JpaRepository<Allowance, Integer> {

}
