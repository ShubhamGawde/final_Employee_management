package com.employeemanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeemanagement.entity.AllotedLeave;

public interface AllotedLeaveRepo extends JpaRepository<AllotedLeave, Integer> {

}
