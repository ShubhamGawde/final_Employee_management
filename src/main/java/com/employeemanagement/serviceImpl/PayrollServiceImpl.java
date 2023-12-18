package com.employeemanagement.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeemanagement.Repository.PayrollRepo;
import com.employeemanagement.Request.PayrollRequest;
import com.employeemanagement.entity.Allowance;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.entity.Leave;
import com.employeemanagement.entity.Payroll;
import com.employeemanagement.entity.Salary;
import com.employeemanagement.exceptionhandler.UserException;
import com.employeemanagement.service.EmployeeService;
import com.employeemanagement.service.PayrollService;

@Service
public class PayrollServiceImpl implements PayrollService {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private LeaveServiceImpl leaveService;

	@Autowired
	private PayrollRepo payrollRepo;

	@Override
	public Payroll calculatePayRoll(int emp_id, PayrollRequest req) throws UserException {

		Employee employee = this.employeeService.getEmployeeById(emp_id);

		List<Leave> unPaidLeaves = this.leaveService.getSortedLeaves(employee.getId(), "Unpaid");

		int unPaidLeavesUnderRange = unPaidLeaves.stream().mapToInt(Leave::getDays).sum();

		Salary salary = employee.getSalary();

		double basicSalary = salary.getBasicSalary();
		List<Allowance> allowances = salary.getAllowances();

		double sum = allowances.stream().mapToDouble(Allowance::getAmount).sum();
		double deductions = salary.getDeductions();
		double salarPerDay = basicSalary / 365;
		double salaryPerMonth = basicSalary / 30;
		double leaveDeduction = salarPerDay * unPaidLeavesUnderRange;

		double paybelSalary = (salaryPerMonth + sum) - (leaveDeduction + deductions);

		Payroll payroll = new Payroll();

		payroll.setSalary(salary);
		payroll.setTotalAmount(paybelSalary);
		payroll.setTotalAllowanceAmount(sum);
		payroll.setServiceDeduction(deductions);
		payroll.setLeaveDeduction(leaveDeduction);
		payroll.setTotalUpaidLeave(unPaidLeavesUnderRange);

		return this.payrollRepo.save(payroll);

	}

}
