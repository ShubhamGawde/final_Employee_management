package com.employeemanagement.configuration;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.employeemanagement.entity.Admin;
import com.employeemanagement.entity.Employee;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;
	private Admin admin;
	private Employee employee;

	public UserDetailsImpl(Admin admin) {
		super();
		this.admin = admin;
	}

	public UserDetailsImpl(Employee employee) {
		super();
		this.employee = employee;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		if (admin != null) {

			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(admin.getRole());
			return List.of(authority);

		} else {

			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(employee.getRole());
			return List.of(authority);

		}

	}

	@Override
	public String getPassword() {
		if (admin != null) {

			return admin.getPassword();

		} else {

			return employee.getPassword();
		}
	}

	@Override
	public String getUsername() {
		if (admin != null) {

			return admin.getEmail();

		} else {

			return employee.getEmail();
		}
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;

	}


}
