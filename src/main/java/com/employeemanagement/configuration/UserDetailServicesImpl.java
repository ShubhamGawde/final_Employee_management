package com.employeemanagement.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.employeemanagement.dao.AdminRepository;
import com.employeemanagement.dao.EmployeeRepository;
import com.employeemanagement.entity.Admin;
import com.employeemanagement.entity.Employee;

@Service
public class UserDetailServicesImpl implements UserDetailsService {

	public UserDetailServicesImpl() {
		super();

	}

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Admin admin = this.adminRepository.getAdminByName(username);
		Employee employee = this.employeeRepository.getByEmployeeByEmail(username);

		if (admin != null) {
			return new UserDetailsImpl(admin);

		} else if (employee != null) {

			return new UserDetailsImpl(employee);

		} else {

			throw new UsernameNotFoundException("Could not found user");
		}

	}

}
