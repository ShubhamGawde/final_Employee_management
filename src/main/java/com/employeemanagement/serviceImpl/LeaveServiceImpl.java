package com.employeemanagement.serviceImpl;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeemanagement.Repository.LeaveRepo;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.entity.Leave;
import com.employeemanagement.entity.LeaveApplication;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.exceptionhandler.UserException;
import com.employeemanagement.service.AllotedLeaveService;
import com.employeemanagement.service.EmployeeService;
import com.employeemanagement.service.LeaveService;

@Service
public class LeaveServiceImpl implements LeaveService {

	@Autowired
	private LeaveRepo leaveRepo;

	@Autowired
	private AllotedLeaveService allotedLeaveService;

	@Autowired
	private EmployeeService empService;

	@Override
	public List<Leave> getAllLeaveOfEmployee(int empId) throws UserException {

		Employee employee_Id = this.empService.getEmployeeById(empId);
		
		LocalDateTime currentDateTime = LocalDateTime.now();

		YearMonth currentYearMonth = YearMonth.from(currentDateTime);

		LocalDateTime startOfMonth = currentYearMonth.atDay(1).atStartOfDay();

		LocalDateTime endOfMonth = currentYearMonth.atEndOfMonth().atTime(23, 59, 59);

		return this.leaveRepo.finAllLeaveOfMonth(employee_Id.getId(), startOfMonth, endOfMonth);
	}

	@Override
	public int getTotalLeave(List<Leave> leaves) {

		int totalDays = leaves.stream().mapToInt(Leave::getDays).sum();

		return totalDays;
	}

	@Override
	public Integer getTotalLeavesCount(int empId) throws UserException {
		List<Leave> allLeaveOfEmployee = this.getAllLeaveOfEmployee(empId);
		long count = allLeaveOfEmployee.stream().count();
		return (int) count;

	}

	@Override
	public List<Leave> getSortedLeaves(int empId, String category) throws UserException {

		List<Leave> allLeaves = this.getAllLeaveOfEmployee(empId);
		if (category.equalsIgnoreCase("All")) 
		{
			return allLeaves;
		}

		return allLeaves.stream().filter(leave -> leave.getCategory().equalsIgnoreCase(category))
				.collect(Collectors.toList());

	}

	@Override
	public Leave addLeave(LeaveApplication leaveApplication) throws CustomeException {

		Leave leave = new Leave();
		leave.setStartDate(leaveApplication.getStart_date());
		leave.setEndDate(leaveApplication.getEnd_date());
		leave.setEmployee(leaveApplication.getEmployee());
		leave.setReason(leaveApplication.getReason());
		leave.setType(leaveApplication.getType());
		leave.setDays(leaveApplication.getTotal_days());
		leave.setCategory(leaveApplication.getCategory());
		leave = this.leaveRepo.save(leave);
		this.allotedLeaveService.deductAllotedLeave(leaveApplication.getEmployee(), leave.getType(),
				leaveApplication.getTotal_days());

		return leave;

	}

}
