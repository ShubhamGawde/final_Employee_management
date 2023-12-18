package com.employeemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Allowance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer allowanceId;

	private String allowance;

	private double amount;

	@ManyToOne
	@JoinColumn(name = "s_id")
	@JsonIgnore
	private Salary salary;

}
