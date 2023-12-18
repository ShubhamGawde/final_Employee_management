package com.employeemanagement.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employeemanagement.Repository.SalaryRepo;
import com.employeemanagement.entity.Allowance;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.entity.Salary;
import com.employeemanagement.exceptionhandler.UserException;
import com.employeemanagement.service.EmployeeService;
import com.employeemanagement.service.SalaryService;

@Service
@Transactional
public class SalaryServiceImpl implements SalaryService {

	@Autowired
	private EmployeeService empService;

	@Autowired
	private SalaryRepo salaryRepo;

	@Override
	public Salary updateSalary(int empId, Salary newSalary) throws UserException {

		Employee employee = this.empService.getEmployeeById(empId);
		Salary salary = employee.getSalary();
		List<Allowance> allowances = salary.getAllowances();

		List<Allowance> newAllowance = newSalary.getAllowances();

		for (Allowance newAl : newAllowance) {

			for (Allowance oAl : allowances) {

				if (newAl.getAllowanceId() == oAl.getAllowanceId()) {

					oAl.setAllowance(newAl.getAllowance());
					oAl.setAmount(newAl.getAmount());
				}

			}
		}

		salary.setBasicSalary(newSalary.getBasicSalary());
		salary.setDeductions(newSalary.getDeductions());


		 return this.salaryRepo.save(salary);
		
		

	}
}
