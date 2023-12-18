package com.employeemanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeemanagement.entity.Holidays;

public interface HolidayRepo extends JpaRepository<Holidays, Integer> {

}
