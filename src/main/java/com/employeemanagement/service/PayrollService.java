package com.employeemanagement.service;

import com.employeemanagement.Request.PayrollRequest;
import com.employeemanagement.entity.Payroll;
import com.employeemanagement.exceptionhandler.UserException;

public interface PayrollService {

	public Payroll calculatePayRoll(int emp_id, PayrollRequest req) throws UserException;

}
