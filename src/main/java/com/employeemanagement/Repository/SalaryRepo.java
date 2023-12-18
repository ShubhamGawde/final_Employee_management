package com.employeemanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeemanagement.entity.Salary;

public interface SalaryRepo extends JpaRepository<Salary, Long> {
	
	

}
