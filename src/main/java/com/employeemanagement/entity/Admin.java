package com.employeemanagement.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
//@Component
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdAt;

	@Column(name = "update_at", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp updateAt;

	@NotNull
	@Size(max = 25, min = 3)
	private String firstName;

	@NotNull
	@Size(max = 25, min = 3)
	private String lastName;

	private String gender;

	@Column(unique = true)
	@Email(regexp = "^(.+)@(.+)$")
	private String email;

	@NotNull
	private String password;

	@NotNull
//	@Pattern(regexp = "^\\+91[7-9][0-9]{10}$", message = "Invalid phone number format")
	private String phone;

	@Lob
	private String image;

	private String role;

	private String linkedin;

	private boolean enable;

	private boolean active;

//	verification code
	@Column(name = "verification_code", updatable = false)
	private String verificationCode;

	public Admin() {
		super();

	}

	public Admin(int id, String firstName, String lastName, String gender, String email, String password, String phone,
			String linkedin, String image, String role, boolean enable) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.linkedin = linkedin;
		this.image = image;
		this.role = role;

		this.enable = enable;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updateAt;
	}

	public void setUpdatedAt(Timestamp updateAt) {
		this.updateAt = updateAt;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getLinkedin() {
		return linkedin;
	}

	public void setLinkedin(String linkedin) {
		this.linkedin = linkedin;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	@Override
	public String toString() {
		return "Admin [admin_id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
				+ ", adminEmail=" + email + ", password=" + password + ", linkedin " + linkedin + ", adminPhone="
				+ phone + ", adminImage=" + image + ", role=" + role + ", enable=" + enable + ", created_at="
				+ ", updated_at=" + "]";
	}

	public Timestamp getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Timestamp updateAt) {
		this.updateAt = updateAt;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
