package com.employeemanagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.employeemanagement.entity.LeaveApplication;
import com.employeemanagement.entity.LeaveStatus;

public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Integer> {

	@Modifying
	@Query("UPDATE LeaveApplication e SET e.status = ?1, e.total_days = ?2 WHERE e.id= ?3 ")
	public void changeStatus(LeaveStatus status, int days, Integer id);

	@Query("SELECT e FROM LeaveApplication e WHERE e.emp_id = ?1 ")
	public List<LeaveApplication> getLeaveApplication(Integer id);
}
