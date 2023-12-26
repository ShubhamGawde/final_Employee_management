package com.employeemanagement.service;

import java.util.List;
import java.util.Set;

import com.employeemanagement.entity.AllotedLeave;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.exceptionhandler.UserException;

public interface AllotedLeaveService {

	public AllotedLeave alloteLeaveToEmployees(Integer leave_id, List<Integer> emp_id);

	public Set<AllotedLeave> getAllotedLeaves(int emp_id) throws UserException;

	public void deductAllotedLeave(Employee emp, String type, int days);

	public AllotedLeave getAlloteleaveById(int altId) throws CustomeException;

}
