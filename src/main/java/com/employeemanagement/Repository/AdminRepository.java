package com.employeemanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.employeemanagement.entity.Admin;

@Transactional

public interface AdminRepository extends JpaRepository<Admin, Integer> {

	@Query("SELECT a FROM Admin a WHERE a.email = ?1")
	public Admin getAdminByName(String email);

	@Modifying
	@Query("UPDATE Admin a SET a.enable = true WHERE a.id= ?1 ")
	public void enable(Integer id);

	@Query("SELECT a FROM Admin a WHERE a.verificationCode = ?1")
	public Admin findByVerificationCode(String verificationCode);

	@Modifying
	@Query("UPDATE Admin a SET a.active = false WHERE a.id= ?1 ")
	public void changeActiveStatus(Integer id);

}
