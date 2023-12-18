package com.employeemanagement.Request;

import com.employeemanagement.entity.AllotedLeave;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LeaveApplicationRequest {

	private String start_date;

	private String end_date;

	private int emp_id;

	private int total_days;

	private AllotedLeave allotedLeave;

	private String reason;

}
