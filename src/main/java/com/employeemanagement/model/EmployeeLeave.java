package com.employeemanagement.model;

public class EmployeeLeave {
   
//	private String id;
	private int days;
	private String reason;

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}


	public EmployeeLeave() {
		super();

	}

	@Override
	public String toString() {
		return "EmployeeLeave [ days=" + days + ", reason=" + reason + "]";
	}

//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}

}
