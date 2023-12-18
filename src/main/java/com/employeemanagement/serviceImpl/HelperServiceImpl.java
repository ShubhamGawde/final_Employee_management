package com.employeemanagement.serviceImpl;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.employeemanagement.service.HelperService;

@Service
public class HelperServiceImpl implements HelperService {

	@Override
	public Map<String, LocalDateTime> getStart_end_of_month() {

		HashMap<String, LocalDateTime> map = new HashMap<>();

		LocalDateTime currentDateTime = LocalDateTime.now();

		YearMonth currentYearMonth = YearMonth.from(currentDateTime);

		LocalDateTime startOfMonth = currentYearMonth.atDay(1).atStartOfDay();

		LocalDateTime endOfMonth = currentYearMonth.atEndOfMonth().atTime(23, 59, 59);

		map.put("startOfMonth", startOfMonth);

		map.put("endOfMonth", endOfMonth);

		return map;

	}

}
