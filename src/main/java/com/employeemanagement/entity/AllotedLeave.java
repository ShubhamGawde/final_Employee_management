package com.employeemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class AllotedLeave {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int altId;

	private int leaveTypeId;

	private int days;

	private String type;

	private String category;
	
	@ManyToOne
	@JoinColumn(name = "emp_id")
	@JsonIgnore
	private Employee employee;

	@Override
	public String toString() {
		return "AllotedLeave [altId=" + altId + ", leaveTypeId=" + leaveTypeId + ", days=" + days + ", type=" + type
				+ "]";
	}

}
