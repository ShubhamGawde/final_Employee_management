package com.employeemanagement.service;

import com.employeemanagement.entity.Salary;
import com.employeemanagement.exceptionhandler.UserException;

public interface SalaryService {
	
	public Salary updateSalary(int empId, Salary Salary) throws UserException;

}
