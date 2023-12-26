package com.employeemanagement.serviceImpl;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeemanagement.Repository.AllotedLeaveRepo;
import com.employeemanagement.Repository.EmployeeRepo;
import com.employeemanagement.Repository.LeaveTypeRepo;
import com.employeemanagement.entity.AllotedLeave;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.entity.LeaveType;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.exceptionhandler.UserException;
import com.employeemanagement.service.AllotedLeaveService;
import com.employeemanagement.service.EmployeeService;

@Service
public class AllotedLeaveServiceImpl implements AllotedLeaveService {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private AllotedLeaveRepo allotedLeaveRepo;
	
	@Autowired
	private LeaveTypeRepo leaveTypeRepo;

	@Override
	public AllotedLeave alloteLeaveToEmployees(Integer leave_id, List<Integer> emp_ids) {

		List<Employee> allEmployees = this.employeeRepo.findAllById(emp_ids);
		LeaveType leaveType = this.leaveTypeRepo.findById(leave_id).get();
		AllotedLeave allotedLeave = null;

		for (Employee emp : allEmployees) {
			allotedLeave = new AllotedLeave();
			allotedLeave.setDays(leaveType.getNumberOfDays());
			allotedLeave.setType(leaveType.getType());
			allotedLeave.setLeaveTypeId(leaveType.getId());
			allotedLeave.setEmployee(emp);
			allotedLeave.setCategory(leaveType.getCategory());

			this.allotedLeaveRepo.save(allotedLeave);
		}

		return allotedLeave;

	}

	@Override
	public Set<AllotedLeave> getAllotedLeaves(int emp_id) throws UserException {
		Employee employee = this.employeeService.getEmployeeById(emp_id);
		return employee.getAllotedLeave();

	}

	@Override
	public void deductAllotedLeave(Employee emp, String type, int days) {

		Set<AllotedLeave> allotedLeave = emp.getAllotedLeave();
		Iterator<AllotedLeave> iterator = allotedLeave.iterator();

		while (iterator.hasNext()) {
			AllotedLeave alt = iterator.next();

			if (alt.getType().equalsIgnoreCase(type)) {
				alt.setDays(alt.getDays() - days);
				break;
			}
		}

		employeeRepo.save(emp);
	}

	@Override
	public AllotedLeave getAlloteleaveById(int altId) throws CustomeException {
		Optional<AllotedLeave> findById = this.allotedLeaveRepo.findById(altId);
		if (findById.isPresent()) {

			return findById.get();
		}
		throw new CustomeException(false, "Leave not found with id : " + altId, 404);
	}

}
