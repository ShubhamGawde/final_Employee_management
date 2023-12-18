package com.employeemanagement.Request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PayrollRequest {

	private int emp_id;

	private LocalDate from;

	private LocalDate to;

}
