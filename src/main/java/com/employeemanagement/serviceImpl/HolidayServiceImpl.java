package com.employeemanagement.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeemanagement.Repository.HolidayRepo;
import com.employeemanagement.entity.Holidays;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.service.HolidayService;

@Service
public class HolidayServiceImpl implements HolidayService {

	@Autowired
	private HolidayRepo holidayRepo;

	@Override
	public Holidays getHolidayById(int h_id) throws CustomeException {

		Optional<Holidays> holiday = this.holidayRepo.findById(h_id);

		if (holiday.isPresent()) {

			return holiday.get();
		}

		throw new CustomeException(false, "holiday not found with id", 404);
	}

	@Override
	public Holidays addNewHoliday(Holidays holiday) {

		System.out.println(holiday);

		return this.holidayRepo.save(holiday);

	}

	@Override
	public Holidays updateHoliday(int h_id, Holidays holiday) throws CustomeException {

		Holidays holidayById = this.getHolidayById(h_id);
		
		System.out.println(holidayById);

		holidayById.setHolidayName(holiday.getHolidayName());

		holidayById.setDate(holiday.getDate());

		return this.holidayRepo.save(holidayById);
	}

	@Override
	public void deleteHoliday(int h_id) throws CustomeException {
		Holidays holidayById = this.getHolidayById(h_id);

		this.holidayRepo.delete(holidayById);

	}

	@Override
	public List<Holidays> getAllHolidays() {

		return this.holidayRepo.findAll();
	}

}
