package com.employeemanagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.employeemanagement.entity.LeaveApplication;

public interface LeaveApplicationRepo extends JpaRepository<LeaveApplication, Integer> {

	@Query("SELECT e FROM LeaveApplication e WHERE e.employee.id = ?1 ")
	public List<LeaveApplication> getLeaveApplication(Integer id);
}
