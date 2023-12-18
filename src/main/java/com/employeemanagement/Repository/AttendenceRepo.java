package com.employeemanagement.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.employeemanagement.entity.Attendence;

@Transactional
public interface AttendenceRepo extends JpaRepository<Attendence, Integer> {

	@Query("SELECT u FROM Attendence u WHERE u.date =?1 AND u.employee.id = ?2")
	public Attendence findByDateAndId(LocalDate date, int emp_id);

	@Query("SELECT u FROM Attendence u WHERE u.date = ?1")
	public List<Attendence> findAllPresentMember(LocalDate date);

	@Query("SELECT a FROM Attendence a WHERE a.employee.id =:empId AND a.createdAt BETWEEN :startOfMonth AND :endOfMonth")
	public List<Attendence> findAttendenceInCurrentMonth(@Param("startOfMonth") LocalDateTime startOfMonth,
			@Param("endOfMonth") LocalDateTime endOfMonth, @Param("empId") int empId);

	List<Attendence> findAllByDate(LocalDate date, Pageable pageable);

}
