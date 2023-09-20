package com.employeemanagement.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Component
@Entity
public class Attendence {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@UpdateTimestamp
	@Column(name = "update_at", nullable = false, updatable = true, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp update_at;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp created_at;

	private int emp_id;

	private Date date;

	private LocalTime in_time;

	private LocalTime out_time;
	
	@Column(columnDefinition = "INTEGER DEFAULT 0")
	private Integer half_day;

	private long working_time;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public LocalTime getIn_time() {
		return in_time;
	}

	public void setIn_time(LocalTime in_time) {
		this.in_time = in_time;
	}

	public LocalTime getOut_time() {
		return out_time;
	}

	public void setOut_time(LocalTime out_time) {
		this.out_time = out_time;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public Integer getHalf_day() {
		return half_day;
	}

	public void setHalf_day(Integer half_day) {
		this.half_day = half_day;
	}

	public long getWorking_time() {
		return working_time;
	}

	public void setWorking_time(long working_time) {
		this.working_time = working_time;
	}

	public Timestamp getUpdate_at() {
		return update_at;
	}

	public void setUpdate_at(Timestamp update_at) {
		this.update_at = update_at;
	}

	@Override
	public String toString() {
		return "Attendence [id=" + id + ", update_at=" + update_at + ", created_at=" + created_at + ", emp_id=" + emp_id
				+ ", date=" + date + ", in_time=" + in_time + ", out_time=" + out_time + ", half_day=" + half_day
				+ ", working_time=" + working_time + "]";
	}

}
