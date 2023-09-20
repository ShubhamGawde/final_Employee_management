package com.employeemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemApplication.class, args);
	}
//
//	@PostConstruct
//	public void init() {
//		// Setting Spring Boot SetTimeZone
//		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
//		System.out.println("hello");
//
//		System.out.println(TimeZone.getDefault());
//		System.out.println("hello");
//	}
}
