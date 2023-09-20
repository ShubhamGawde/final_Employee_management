package com.employeemanagement.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Employee {

//	Mendatory  info
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp created_at;

	@UpdateTimestamp
	@Column(name = "update_at", nullable = false, updatable = true, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp update_at;

	@NotNull
	@Size(max = 25, min = 3)
	private String firstName;

	@NotNull
	@Size(max = 25, min = 3)
	private String lastName;

	@Column(unique = true)
	@Email(regexp = "^(.+)@(.+)$")
	private String email;

	@NotNull
	private String password;

	@NotNull
	@Pattern(regexp = "(^$|[0-9]{10})")
	private String phone;

//	option info or personal info

	private String profileImg;

	private String gender;

	private String maritalStatus;

	private String personalEmail;

	private String alterPhone;

	private String dob;

	private String permanentAddr;

	private String currentAddr;

	// official info

	private String role;

	private String jobTitle;

	private boolean enabled;

	private boolean active;

	private boolean is_deleted;

	private String type;

	private String joiningDate;

	private String workLocation;

	private String experience;

//	Educational Info

	private String qualification;

	private String stream;

	private String college;

	private String university;

//	Required Document Info;

	private String adhar;

	private String result;

	private String degree;

//	verification code
	@Column(name = "verification_code", updatable = false)
	private String verificationCode;

	public Employee(int id, String firstName, String lastName, String email, String password, String phone,
			String profileImg, String gender, String maritalStatus, String personalEmail, String alterPhone, String dob,
			String permanentAddr, String currentAddr, String role, String jobTitle, boolean enabled, String type,
			String joiningDate, String workLocation, String experience, String qualification, String stream,
			String college, String university, String adhar, String result, String degree) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.profileImg = profileImg;
		this.gender = gender;
		this.maritalStatus = maritalStatus;
		this.personalEmail = personalEmail;
		this.alterPhone = alterPhone;
		this.dob = dob;
		this.permanentAddr = permanentAddr;
		this.currentAddr = currentAddr;
		this.role = role;
		this.jobTitle = jobTitle;
		this.enabled = enabled;
		this.type = type;
		this.joiningDate = joiningDate;
		this.workLocation = workLocation;
		this.experience = experience;
		this.qualification = qualification;
		this.stream = stream;
		this.college = college;
		this.university = university;
		this.adhar = adhar;
		this.result = result;
		this.degree = degree;
	}

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getPersonalEmail() {
		return personalEmail;
	}

	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}

	public String getAlterPhone() {
		return alterPhone;
	}

	public void setAlterPhone(String alterPhone) {
		this.alterPhone = alterPhone;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPermanentAddr() {
		return permanentAddr;
	}

	public void setPermanentAddr(String permanentAddr) {
		this.permanentAddr = permanentAddr;
	}

	public String getCurrentAddr() {
		return currentAddr;
	}

	public void setCurrentAddr(String currentAddr) {
		this.currentAddr = currentAddr;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getWorkLocation() {
		return workLocation;
	}

	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getAdhar() {
		return adhar;
	}

	public void setAdhar(String adhar) {
		this.adhar = adhar;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public boolean isEnabled() {
		return enabled;
	}
//
//	@Override
//	public String toString() {
//		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
//				+ ", password=" + password + ", phone=" + phone + ", profileImg=" + profileImg + ", gender=" + gender
//				+ ", maritalStatus=" + maritalStatus + ", personalEmail=" + personalEmail + ", alterPhone=" + alterPhone
//				+ ", dob=" + dob + ", permanentAddr=" + permanentAddr + ", currentAddr=" + currentAddr + ", role="
//				+ role + ", jobTitle=" + jobTitle + ", enabled=" + enabled + ", type=" + type + ", joiningDate="
//				+ joiningDate + ", workLocation=" + workLocation + ", experience=" + experience + ", qualification="
//				+ qualification + ", stream= " + stream + ", college=" + college + ", university=" + university
//				+ ", adhar=" + adhar + ", result=" + result + ", degree=" + degree + "]";
//	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public Timestamp getUpdate_at() {
		return update_at;
	}

	public void setUpdate_at(Timestamp update_at) {
		this.update_at = update_at;
	}

	public boolean isIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
