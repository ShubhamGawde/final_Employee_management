package com.employeemanagement.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class LeaveApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int application_id;

	private String start_date;

	private String end_date;

	private String half_day;

	private LeaveStatus status;

	private int emp_id;

	private int total_days;

	@Transient
	private int flag;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdAt;

	@UpdateTimestamp
	@Column(name = "update_at", nullable = false, updatable = true, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp update_at;

	public LeaveApplication() {
		super();
	}

	public LeaveApplication(int application_id, String start_date, String end_date, int emp_id) {
		super();
		this.application_id = application_id;
		this.start_date = start_date;
		this.end_date = end_date;
		this.emp_id = emp_id;
	}

	public int getLeave_id() {
		return application_id;
	}

	public void setLeave_id(int leave_id) {
		this.application_id = leave_id;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public int getApplication_id() {
		return application_id;
	}

	public void setApplication_id(int application_id) {
		this.application_id = application_id;
	}

	public LeaveStatus getStatus() {
		return status;
	}

	public void setStatus(LeaveStatus status) {
		this.status = status;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdate_at() {
		return update_at;
	}

	public void setUpdate_at(Timestamp update_at) {
		this.update_at = update_at;
	}

	public int getTotal_days() {
		return total_days;
	}

	public void setTotal_days(int total_days) {
		this.total_days = total_days;
	}

	public String getHalf_day() {
		return half_day;
	}

	public void setHalf_day(String half_day) {
		this.half_day = half_day;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}
