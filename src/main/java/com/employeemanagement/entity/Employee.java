package com.employeemanagement.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee {

//	Mendatory  info
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonIgnore
	private Timestamp created_at;

	@UpdateTimestamp
	@Column(name = "update_at", nullable = false, updatable = true, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@JsonIgnore
	private Timestamp update_at;

	private String firstName;

	private String lastName;

	@Column(unique = true)
	private String email;

	@JsonIgnore
	private String password;

	private String phone;

//	option info or personal info

	private String profileImgUrl;

	private String gender;

	private String maritalStatus;

	private String alterPhone;

	private String dob;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Address> address = new ArrayList<>();

	// official info

	private String role;

	private String jobTitle;

	private boolean enabled;

	private boolean active;

	private boolean deleted;

	private String type;

	private String joiningDate;

	private String workLocation;

	private String experience;

	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private Salary salary;

	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<AllotedLeave> allotedLeave = new HashSet<AllotedLeave>();

//	Educational Info

	private String qualification;

	private String stream;

	private String college;

	private String university;


}
