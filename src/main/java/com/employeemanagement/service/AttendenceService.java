package com.employeemanagement.service;

import java.time.LocalDate;
import java.util.List;

import com.employeemanagement.entity.Attendence;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.exceptionhandler.UserException;

public interface AttendenceService {

	public List<Attendence> getAllPresentEmployee();

	public Attendence setChecking(int id) throws UserException, CustomeException;

	public Attendence setCheckOut(int empId) throws UserException, CustomeException;
	
	public boolean isCheckIn(int empId) throws UserException;

	public List<Attendence> getAllAttendenceOfEmployee(int emp_id) throws UserException;
	
	public List<Attendence>getAttendenceFilterByDate(LocalDate date, int page, int size);
}
