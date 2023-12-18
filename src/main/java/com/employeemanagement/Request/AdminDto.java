package com.employeemanagement.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminDto {

	@NotEmpty(message = "firstName is required")
	@Size(max = 25, min = 3)
	private String firstName;

	@NotEmpty(message = "lastName is required")
	@Size(max = 25, min = 3)
	private String lastName;

	private String gender;

	@NotEmpty(message = "email is required")
	@Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
			+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
	private String email;

	@NotEmpty(message = "password is required")
	private String password;

//	@NotNull
//	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid phone number format")
	private String phone;

	private String image;

}
