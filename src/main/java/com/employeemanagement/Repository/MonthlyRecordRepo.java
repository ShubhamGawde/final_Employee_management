package com.employeemanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.employeemanagement.entity.MonthlyLeaveRecord;

public interface MonthlyRecordRepo extends JpaRepository<MonthlyLeaveRecord, Integer> {

}
