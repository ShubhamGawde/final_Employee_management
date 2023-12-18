package com.employeemanagement.serviceImpl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeemanagement.Repository.LeaveApplicationRepo;
import com.employeemanagement.Request.LeaveApplicationRequest;
import com.employeemanagement.Response.Response;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.entity.LeaveApplication;
import com.employeemanagement.entity.LeaveStatus;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.exceptionhandler.UserException;
import com.employeemanagement.service.EmployeeService;
import com.employeemanagement.service.LeaveApplicationService;
import com.employeemanagement.service.LeaveService;

import jakarta.transaction.Transactional;

@Service
public class LeaveApplicationServiceImpl implements LeaveApplicationService {

	@Autowired
	private LeaveApplicationRepo applicationRepo;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private LeaveService leaveService;

	@Override
	public LeaveApplication creatingLeaveApplication(LeaveApplicationRequest applicationReq) throws UserException {

		Employee employee = this.employeeService.getEmployeeById(applicationReq.getEmp_id());

		LeaveApplication leaveApplication = new LeaveApplication();

		leaveApplication.setStart_date(LocalDate.parse(applicationReq.getStart_date()));
		leaveApplication.setEnd_date(LocalDate.parse(applicationReq.getEnd_date()));
		leaveApplication.setReason(applicationReq.getReason());
		leaveApplication.setTotal_days(applicationReq.getTotal_days());
		leaveApplication.setEmployee(employee);
		leaveApplication.setStatus(LeaveStatus.PENDING);
		leaveApplication.setAllotedLeaveId(applicationReq.getAllotedLeave().getAltId());
		leaveApplication.setType(applicationReq.getAllotedLeave().getType());
		leaveApplication.setAllotedLeaveDays(applicationReq.getAllotedLeave().getDays());
		leaveApplication.setCategory(applicationReq.getAllotedLeave().getCategory());

		return this.applicationRepo.save(leaveApplication);

	}

	@Override
	public List<LeaveApplication> showAllApplication() {

		return this.applicationRepo.findAll();

	}

	@Transactional
	@Override
	public Response setLeaveStatus(int app_id, LeaveStatus status) throws CustomeException {

		LeaveApplication leaveApplication = this.getLeaveApplication(app_id);

		if (status == LeaveStatus.APPROVED) {

			int daysBetween = ((int) ChronoUnit.DAYS.between(leaveApplication.getStart_date(),
					leaveApplication.getEnd_date())) + 1;
			leaveApplication.setStatus(status);
			leaveApplication.setTotal_days(daysBetween);
			leaveApplication = this.applicationRepo.save(leaveApplication);

			this.leaveService.addLeave(leaveApplication);


		} else {
			leaveApplication.setStatus(status);
			leaveApplication.setTotal_days(0);
			leaveApplication = this.applicationRepo.save(leaveApplication);
		}
		return new Response(true, "leave is " + leaveApplication.getStatus().name(), leaveApplication);
	}

	@Override
	public List<LeaveApplication> getEmployeeApplicationByEmpId(int emp_id) throws UserException {

		Employee employee = this.employeeService.getEmployeeById(emp_id);

		return this.applicationRepo.getLeaveApplication(employee.getId());
	}

	@Override
	public LeaveApplication getLeaveApplication(int id) throws CustomeException {

		Optional<LeaveApplication> leaveApplication = this.applicationRepo.findById(id);
		if (leaveApplication.isPresent()) {

			return leaveApplication.get();
		}
		throw new CustomeException(false,"no application found with this application id : " + id, 404);
	}

}
