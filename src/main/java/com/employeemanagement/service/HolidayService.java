package com.employeemanagement.service;

import java.util.List;

import com.employeemanagement.entity.Holidays;
import com.employeemanagement.exceptionhandler.CustomeException;

public interface HolidayService {

	public Holidays getHolidayById(int h_id) throws CustomeException;

	public Holidays addNewHoliday(Holidays holiday);

	public Holidays updateHoliday(int h_id, Holidays holiday) throws CustomeException;

	public void deleteHoliday(int h_id) throws CustomeException;

	public List<Holidays> getAllHolidays();

}
