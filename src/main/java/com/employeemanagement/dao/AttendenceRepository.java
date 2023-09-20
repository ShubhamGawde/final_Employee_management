package com.employeemanagement.dao;

import java.sql.Date;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.employeemanagement.entity.Attendence;

@Transactional
public interface AttendenceRepository extends JpaRepository<Attendence, Integer> {

	@Query("SELECT u FROM Attendence u WHERE u.date LIKE ?1% AND u.emp_id = ?2")
	public Attendence findByDateAndId(String date, int emp_id);

	@Modifying
	@Query(value = "INSERT INTO Attendence (emp_id, in_time, out_time, half_day, working_time, date) values (?1, ?2, ?3, ?4, ?5, ?6)", nativeQuery = true)
	public Integer addAttendence(int emp_id, LocalTime inTime, LocalTime outTime, int half_day, long working_time,
			Date date);

	@Modifying
	@Query("UPDATE Attendence u SET u.out_time = ?1, u.half_day = ?2, u.working_time = ?3 WHERE u.id = ?4")
	public void updateAttendence(LocalTime outTime, int halfday, long working_time, int id);

	@Query("SELECT u FROM Attendence u WHERE u.date = ?1")
	public List<Attendence> findAllPresentMember(Date date);

	@Query("SELECT a FROM Attendence a WHERE a.emp_id = ?1")
	public List<Attendence> findAllByEmpId(Integer id);

	@Query(value = "SELECT * FROM Attendence WHERE( (DATE(date) = CURDATE() - INTERVAL 1 DAY)  OR (DATE(date) = CURDATE() - INTERVAL 2 DAY)) AND (emp_id = ?1)", nativeQuery = true)
	public List<Attendence> getPreviousDays(int id);
}
