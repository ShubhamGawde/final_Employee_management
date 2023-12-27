package com.employeemanagement.service;

import java.util.List;

import com.employeemanagement.Request.LeaveApplicationRequest;
import com.employeemanagement.Response.Response;
import com.employeemanagement.entity.LeaveApplication;
import com.employeemanagement.entity.LeaveStatus;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.exceptionhandler.UserException;

public interface LeaveApplicationService {

	public Response setLeaveStatus(int app_id, LeaveStatus status) throws  CustomeException;

	public LeaveApplication creatingLeaveApplication(LeaveApplicationRequest applicationReq,Integer altId, Integer usrId) throws UserException, CustomeException;

	public List<LeaveApplication> showAllApplication();

	public List<LeaveApplication> getEmployeeApplicationByEmpId(int emp_id) throws UserException;

	public LeaveApplication getLeaveApplication(int id) throws CustomeException;
}
