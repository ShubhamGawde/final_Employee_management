package com.employeemanagement.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.employeemanagement.entity.Leave;

public interface LeaveRepo extends JpaRepository<Leave, Integer> {

	@Query("Select e from Leave e where e.employee.id = :id And (e.createdAt BETWEEN :start AND :end)")
	public List<Leave> finAllLeaveOfMonth(@Param("id") int id, @Param("start") LocalDateTime start,
			@Param("end") LocalDateTime end);


}
