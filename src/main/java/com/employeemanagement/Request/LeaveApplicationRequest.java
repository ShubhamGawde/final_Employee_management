package com.employeemanagement.Request;

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

	private int total_days;

	private Integer allotedLeaveId;

	private String reason;

}
