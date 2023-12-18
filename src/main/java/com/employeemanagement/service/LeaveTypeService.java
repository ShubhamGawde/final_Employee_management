package com.employeemanagement.service;

import java.util.List;

import com.employeemanagement.entity.LeaveType;
import com.employeemanagement.exceptionhandler.CustomeException;

public interface LeaveTypeService {

	public LeaveType addNewLeaveType(LeaveType leaveType);

	public LeaveType updateLeaveType(int leaveType_id, LeaveType leleaveType) throws CustomeException;

	public void removeLeaveType(int leaveType_id) throws CustomeException;

	public List<LeaveType> getAllLeaveType();

	public LeaveType getLeaveTypeById(int leaveType_id) throws CustomeException;

}
