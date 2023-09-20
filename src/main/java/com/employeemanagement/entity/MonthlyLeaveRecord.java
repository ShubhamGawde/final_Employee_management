package com.employeemanagement.entity;

//import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Component
public class MonthlyLeaveRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp created_at;
	private int emp_id;
	@Column(columnDefinition = "INTEGER DEFAULT 0")
	private int fullDayLeaves;
	@Column(columnDefinition = "INTEGER DEFAULT 0")
	private int halfDayLeaves;
	@Column(columnDefinition = "FLOAT DEFAULT 0.0")
	private float totalLeaves;
	@Column(columnDefinition = "FLOAT DEFAULT 0.0")
	private float total_pl;
	@Column(columnDefinition = "FLOAT DEFAULT 0.0")
	private float deduction;
	@Column(columnDefinition = "FLOAT DEFAULT 0.0")
	private float extraLeaves;
	@Column(columnDefinition = "FLOAT DEFAULT 0.0")
	private float remaining_pl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public int getFullDayLeaves() {
		return fullDayLeaves;
	}

	public void setFullDayLeaves(int fullDayLeaves) {
		this.fullDayLeaves = fullDayLeaves;
	}

	public int getHalfDayLeaves() {
		return halfDayLeaves;
	}

	public void setHalfDayLeaves(int halfDayLeaves) {
		this.halfDayLeaves = halfDayLeaves;
	}

	public float getTotalLeaves() {
		return totalLeaves;
	}

	public void setTotalLeaves(float totalLeaves) {
		this.totalLeaves = totalLeaves;
	}

	public float getTotal_pl() {
		return total_pl;
	}

	public void setTotal_pl(float total_pl) {
		this.total_pl = total_pl;
	}

	public float getDeduction() {
		return deduction;
	}

	public void setDeduction(float deduction) {
		this.deduction = deduction;
	}

	public float getExtraLeaves() {
		return extraLeaves;
	}

	public void setExtraLeaves(float extraLeaves) {
		this.extraLeaves = extraLeaves;
	}

	public float getRemaining_pl() {
		return remaining_pl;
	}

	public void setRemaining_pl(float remaining_pl) {
		this.remaining_pl = remaining_pl;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

}
