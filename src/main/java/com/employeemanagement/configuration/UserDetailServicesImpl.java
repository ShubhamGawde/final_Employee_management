package com.employeemanagement.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.employeemanagement.Repository.AdminRepository;
import com.employeemanagement.Repository.EmployeeRepo;
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
	private EmployeeRepo employeeRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Employee employee = this.employeeRepository.findByEmail(username);

		if (employee != null)
		{
			return new UserDetailsImpl(employee);
		}
		
		Admin admin = this.adminRepository.getAdminByName(username);
		
		if (admin != null) 
		{
			return new UserDetailsImpl(admin);
		}
		
		throw new UsernameNotFoundException("Invalid Username: " + username);
		

	}

}
