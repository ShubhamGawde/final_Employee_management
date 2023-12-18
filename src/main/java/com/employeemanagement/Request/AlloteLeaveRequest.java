package com.employeemanagement.Request;

import java.util.List;

import com.employeemanagement.entity.LeaveType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlloteLeaveRequest {

	private LeaveType leaveType;

	private List<Integer> emp_ids;

}
