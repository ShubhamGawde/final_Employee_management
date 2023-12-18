package com.employeemanagement.Request;

import java.util.ArrayList;
import java.util.List;
import com.employeemanagement.entity.Address;
import com.employeemanagement.entity.Salary;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDto {
	
	private int id;

	@NotNull
	@Size(max = 25, min = 3)
	private String firstName;

	@NotNull
	@Size(max = 25, min = 3)
	private String lastName;

	@NotEmpty(message = "email is required")
	@Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
			+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
	private String email;

	@NotEmpty(message = "password is required")
	private String password;

	@NotNull
//	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid phone number format")
	private String phone;

	private String gender;

	private String maritalStatus;

	private String alterPhone;

	private String dob;
	
	private String profileImgUrl;

	private List<Address> address = new ArrayList<>();

	private String jobTitle;

	private String type;

	private String joiningDate;

	private int leaveDays;

	@NotNull
	private Salary salary;

	private String workLocation;

	private String experience;

	private String qualification;

	private String stream;

	private String college;

	private String university;
	
	private String role;
	
	private boolean enabled;

	private boolean active;
	}
