package com.employeemanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeemanagement.entity.Payroll;

public interface PayrollRepo extends JpaRepository<Payroll, Integer> {

}
