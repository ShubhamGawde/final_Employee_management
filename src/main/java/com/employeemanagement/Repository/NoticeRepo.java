package com.employeemanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeemanagement.entity.Notice;

public interface NoticeRepo extends JpaRepository<Notice, Integer> {

}
