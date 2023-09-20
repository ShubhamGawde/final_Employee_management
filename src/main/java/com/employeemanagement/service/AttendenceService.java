package com.employeemanagement.service;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeemanagement.dao.AttendenceRepository;
import com.employeemanagement.dao.EmployeeRepository;
import com.employeemanagement.entity.Attendence;

@Service
public class AttendenceService {

	@Autowired
	private AttendenceRepository attendenceRepo;

	@Autowired
	EmployeeRepository employeeRepo;

	@Autowired
	Attendence attendence;

	public boolean addAttendence(int id) {

		LocalDateTime ldt = LocalDateTime.now();

		Attendence attendence = this.attendenceRepo

				.findByDateAndId(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(ldt), id);

		if (attendence == null) {
			java.util.Date currentDate = new java.util.Date();

			Date sqlDate = new Date(currentDate.getTime());
//			System.out.println(sqlDate);
			long working_time = 0;
			List<Attendence> previousDays = this.attendenceRepo.getPreviousDays(id);
			Attendence previousDay1 = previousDays.get(0);
			Attendence previousDay2 = previousDays.get(1);
			int day1 = previousDay1.getIn_time().withSecond(0).withNano(0).compareTo(LocalTime.parse("10:20:00"));
			int day2 = previousDay2.getIn_time().withSecond(0).withNano(0).compareTo(LocalTime.parse("10:20:00"));
//			System.out.println("day1 " + day1 + " day2" + day2);
			if ((day1 > 0 && day2 > 0) && ((previousDay1.getHalf_day() == 0) && (previousDay2.getHalf_day() == 0))) {

				this.attendenceRepo.addAttendence(id, LocalTime.now(), null, 1, working_time, sqlDate);

			} else {

				this.attendenceRepo.addAttendence(id, LocalTime.now(), null, 0, working_time, sqlDate);

			}
			System.out.println("Attenedence Registered Successfully");

			return true;

		} else {

			return false;
		}

	}

	public int setCheckOut(int empId) {

		LocalDateTime ldt = LocalDateTime.now();
		Attendence attendence = attendenceRepo
				.findByDateAndId(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(ldt), empId);

		if (attendence == null) {

			return 0;

		} else if (attendence != null && attendence.getOut_time() == null) {

			LocalTime out_time = LocalTime.now();
			LocalTime inTime = attendence.getIn_time();

			Duration duration = Duration.between(inTime, out_time);
//			long hours = duration.toHours();
			long minutes = duration.toMinutes();
			System.out.println(minutes);

			if (minutes < 300) {

				this.attendenceRepo.updateAttendence(out_time, 1, minutes, attendence.getId());

			} else {

				this.attendenceRepo.updateAttendence(out_time, 0, minutes, attendence.getId());

			}

//			System.out.println(String.format("%02d:%02d", hours, minutes));

			return 1;

		} else {

			return 2;

		}

	}

	public List<Attendence> getAllPresentEmployee() {

		List<Attendence> findAllPresentMember = this.attendenceRepo.findAllPresentMember(Date.valueOf(LocalDate.now()));

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

	public String demo(int id) {

		List<Attendence> previousDays = this.attendenceRepo.getPreviousDays(id);
		Attendence previousDay1 = previousDays.get(0);
		Attendence previousDay2 = previousDays.get(1);
		System.out.println(previousDay1);
		System.out.println(previousDay2);
		int day1 = previousDay1.getIn_time().withSecond(0).withNano(0).compareTo(LocalTime.parse("10:15:00"));
		int day2 = previousDay2.getIn_time().withSecond(0).withNano(0).compareTo(LocalTime.parse("10:15:00"));
		System.out.println("day1 = " + day1 + " day2 = " + day2);
		return "success";
	}

}
