package com.employeemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.Response.Response;
import com.employeemanagement.entity.Holidays;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.service.HolidayService;

@RestController
@RequestMapping("/api")
public class HolidayController {

	@Autowired
	private HolidayService holidayService;

	@PostMapping("/admin/add_holiday")
	public ResponseEntity<Response> addNewHoliday(@RequestBody Holidays holiday) 
	{
		Holidays newHoliday = this.holidayService.addNewHoliday(holiday);

		return new ResponseEntity<>(new Response(true, "holiday created", newHoliday), HttpStatus.CREATED);
	}

	@PostMapping("/admin/update_holiday/{h_id}")
	public ResponseEntity<Response> updateHoliday(@RequestBody Holidays holiday, @PathVariable("h_id") int h_id)
			throws CustomeException 
	{
		Holidays updatedHoliday = this.holidayService.updateHoliday(h_id, holiday);

		return new ResponseEntity<>(new Response(true, "updated successfully", updatedHoliday), HttpStatus.OK);
	}

	@DeleteMapping("/admin/remove_holiday/{h_id}")
	public ResponseEntity<String> deleteHoliday(@PathVariable("h_id") int h_id) throws CustomeException 
	{
		this.holidayService.deleteHoliday(h_id);

		return ResponseEntity.ok("holiday deleted successfully");
	}

	@GetMapping("/get_all_holiday")
	public ResponseEntity<List<Holidays>> getAllHolidays() 
	{
		List<Holidays> allHolidays = this.holidayService.getAllHolidays();

		return ResponseEntity.ok(allHolidays);
	}

	@GetMapping("/get_holiday/{h_id}")
	public ResponseEntity<Response> getHolidayById(@PathVariable("h_id") int h_id) throws CustomeException 
	{
		Holidays holidayById = this.holidayService.getHolidayById(h_id);

		return new ResponseEntity<Response>(new Response(true, " available holiday", holidayById), HttpStatus.OK);
	}

}
