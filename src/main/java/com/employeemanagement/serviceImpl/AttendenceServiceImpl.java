package com.employeemanagement.serviceImpl;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.employeemanagement.Repository.AttendenceRepo;
import com.employeemanagement.entity.Attendence;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.exceptionhandler.UserException;
import com.employeemanagement.service.AttendenceService;

@Service
public class AttendenceServiceImpl implements AttendenceService {

	@Autowired
	private AttendenceRepo attendenceRepo;

	@Autowired
	private EmployeeServiceImpl employeeService;

	@Override
	public boolean isCheckIn(int empId) throws UserException {

		Employee employee = this.employeeService.getEmployeeById(empId);

		Attendence attendence = this.attendenceRepo.findByDateAndId(LocalDate.now(), employee.getId());

		return attendence == null || !attendence.isCheckin() ? false : true;
	}

	@Override
	public Attendence setChecking(int id) throws UserException, CustomeException {

		Employee employee = this.employeeService.getEmployeeById(id);
		Attendence attendence = this.attendenceRepo.findByDateAndId(LocalDate.now(), employee.getId());
		if(attendence != null)
		{
			throw new CustomeException(false, "You are already checkedin. CheckIN  on next day !!!!", 400);
		}
		Attendence createAttendence = new Attendence();
		createAttendence.setIn_time(LocalTime.now());
		createAttendence.setDate(LocalDate.now());
		createAttendence.setHalf_day(0);
		createAttendence.setWorking_time(0);
		createAttendence.setEmployee(employee);
		createAttendence.setCheckin(true);
		createAttendence.setCheckout(false);
		Attendence savedAttendence = this.attendenceRepo.save(createAttendence);
		return savedAttendence;

	}

	@Override
	public Attendence setCheckOut(int empId) throws UserException, CustomeException {

		Employee employee = this.employeeService.getEmployeeById(empId);

		Attendence attendence = this.attendenceRepo.findByDateAndId(LocalDate.now(), employee.getId());

		if (attendence == null) {

			throw new CustomeException(false, " checked in first", 400);

		} else if (attendence != null && attendence.isCheckout()) {

			throw new CustomeException(false, "You already have checked out", 400);

		}

		else {
			Duration duration = Duration.between(attendence.getIn_time(), LocalTime.now());
			Long totalTime = duration.toMinutes();

			attendence.setOut_time(LocalTime.now());
			attendence.setWorking_time(totalTime);
			attendence.setCheckout(true);
			attendence.setCheckin(false);

			if (totalTime < 300)
				attendence.setHalf_day(1);

			else
				attendence.setHalf_day(0);

			return this.attendenceRepo.save(attendence);

		}

	}

	@Override
	public List<Attendence> getAllPresentEmployee() {

		List<Attendence> findAllPresentMember = this.attendenceRepo.findAllPresentMember(LocalDate.now());

		return findAllPresentMember;

	}

	public String demoforSundayAndSaturday() {

		int year = LocalDate.now().getYear();
		int month = LocalDate.now().getMonthValue();

		LocalDate startDate = LocalDate.of(year, month, 1);
		LocalDate endDate = startDate.plusMonths(1).minusDays(1);
		System.out.println(endDate);

		int saturdaysAndSundaysCount = 0;

		while (!startDate.isAfter(endDate)) {
			DayOfWeek dayOfWeek = startDate.getDayOfWeek();
			if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
				saturdaysAndSundaysCount++;
			}
			startDate = startDate.plusDays(1);
		}

		System.out.println(saturdaysAndSundaysCount);

		return "success";
	}

	@Override
	public List<Attendence> getAllAttendenceOfEmployee(int emp_id) throws UserException {

		Employee employee = this.employeeService.getEmployeeById(emp_id);

		LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
		LocalDateTime endOfMonth = LocalDateTime.of(LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()),
				LocalTime.MAX);

		return this.attendenceRepo.findAttendenceInCurrentMonth(startOfMonth, endOfMonth, employee.getId());

	}

	@Override
	public List<Attendence> getAttendenceFilterByDate(LocalDate date, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return this.attendenceRepo.findAllByDate(date, pageable);

	}

}
