package com.employeemanagement.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payroll {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int payrollId;

	private int totalUpaidLeave;
	@ManyToOne
	@JoinColumn(name = "s_id")
	private Salary salary;

	private double totalAllowanceAmount;

	private double serviceDeduction;

	private double leaveDeduction;

	private double totalAmount;

	@CreationTimestamp
	private Timestamp createdAt;

}
