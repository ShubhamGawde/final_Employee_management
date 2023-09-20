package com.employeemanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.employeemanagement.entity.MonthlyLeaveRecord;

public interface MonthlyRecordRepository extends JpaRepository<MonthlyLeaveRecord, Integer> {

}
