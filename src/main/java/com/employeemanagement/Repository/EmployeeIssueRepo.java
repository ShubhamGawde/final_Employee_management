package com.employeemanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeemanagement.entity.EmployeeIssueRequest;

public interface EmployeeIssueRepo extends JpaRepository<EmployeeIssueRequest, Integer> {

}
