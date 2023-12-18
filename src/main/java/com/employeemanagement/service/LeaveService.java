package com.employeemanagement.service;

import java.util.List;

import com.employeemanagement.entity.Leave;
import com.employeemanagement.entity.LeaveApplication;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.exceptionhandler.UserException;

public interface LeaveService {

	public List<Leave> getAllLeaveOfEmployee(int empId) throws UserException;

	public Leave addLeave(LeaveApplication leaveApplication) throws CustomeException;

	public Integer getTotalLeavesCount(int empId) throws UserException;

	public List<Leave> getSortedLeaves(int empIdF, String category) throws UserException;

	public int getTotalLeave(List<Leave> leaves);
}
