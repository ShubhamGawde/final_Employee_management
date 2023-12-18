package com.employeemanagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.employeemanagement.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

	@Query("SELECT e FROM Employee e WHERE e.email =?1")
	public Employee findByEmail(String email);

	public Employee findById(int id);

	@Query(value = "SELECT COUNT(*) FROM  Employee", nativeQuery = true)
	public int getTotalEmployeeByColumn();

	@Modifying
	@Query("UPDATE Employee e SET e.enabled = true WHERE e.id= ?1 ")
	public void enable(Integer id);

//	@Query("SELECT e FROM Employee e WHERE e.verificationCode = ?1")
//	public Employee findByVerificationCode(String verificationCode);

	@Modifying
	@Query("UPDATE Employee e SET e.deleted = true WHERE e.id= ?1 ")
	public void delete(Integer id);

	@Query("SELECT e FROM Employee e WHERE e.deleted = false")
	public List<Employee> getAllEmployee();

	@Query("UPDATE Admin a SET a.active = false WHERE a.id= ?1 ")
	public void changeActiveStatus(Integer id);
}
