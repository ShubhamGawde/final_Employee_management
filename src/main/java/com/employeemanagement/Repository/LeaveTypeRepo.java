package com.employeemanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeemanagement.entity.LeaveType;

public interface LeaveTypeRepo extends JpaRepository<LeaveType, Integer> {

}
