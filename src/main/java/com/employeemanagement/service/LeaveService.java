package com.employeemanagement.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeemanagement.dao.LeaveApplicationRepository;
import com.employeemanagement.entity.LeaveApplication;
import com.employeemanagement.entity.LeaveStatus;

import jakarta.transaction.Transactional;

@Service
public class LeaveService {

	@Autowired
	private LeaveApplicationRepository applicationRepo;

	public LeaveApplication creatingLeaveApplication(LeaveApplication leaveApplication) {

		leaveApplication.setStatus(LeaveStatus.PENDING); // Status 1
//		leaveApplication.setStatus(LeaveStatus.APPROVED);// Status 0
//		leaveApplication.setStatus(LeaveStatus.REJECTED); // Status 2

		return this.applicationRepo.save(leaveApplication);

	}

	public Map<String, Object> showAllApplication() {

		Map<String, Object> response = new HashMap<>();

		List<LeaveApplication> allApplications = this.applicationRepo.findAll();

		response.put("success", true);
		response.put("code", 200);
		response.put("applications", allApplications);
		return response;

	}

	@Transactional
	public Map<String, Object> setStatus(LeaveApplication leaveApplication) {
		Map<String, Object> response = new HashMap<>();

		LeaveStatus status = leaveApplication.getStatus();

		int daysBetween = 0;

		if (status == LeaveStatus.APPROVED) {
			if (leaveApplication.getHalf_day() == null) {

				LocalDate fromDate = LocalDate.parse(leaveApplication.getStart_date());
				LocalDate toDate = LocalDate.parse(leaveApplication.getEnd_date());

				// Calculate the number of days between "from" and "to" dates
				daysBetween = ((int) ChronoUnit.DAYS.between(fromDate, toDate)) + 1;

				this.applicationRepo.changeStatus(status, daysBetween, leaveApplication.getApplication_id());
			} else {

				this.applicationRepo.changeStatus(status, daysBetween, leaveApplication.getApplication_id());
			}

			response.put("success", true);
			response.put("message", " leave is approved");
			response.put("code", 200);
			return response;

		} else {

			this.applicationRepo.changeStatus(status, daysBetween, leaveApplication.getApplication_id());
//			System.out.println("leave application is rejected");
			response.put("success", true);
			response.put("message", " leave is rejected");
			response.put("code", 200);
			return response;

		}

	}

	public List<LeaveApplication> getApplicationStatus(int id) {

		return this.applicationRepo.getLeaveApplication(id);
	}

}
