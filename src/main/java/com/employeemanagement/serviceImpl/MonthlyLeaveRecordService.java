//package com.employeemanagement.service;
//
//import java.time.DayOfWeek;
//import java.time.LocalDate;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MonthlyLeaveRecordService {
//
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
//
//	public void executeSQLQueries() {
//
//		LocalDate currentDate = LocalDate.now();
//
//		int year = currentDate.getYear();
//		int month = currentDate.getMonthValue();
//
//		LocalDate startDate = LocalDate.of(year, month, 1);
//		LocalDate endDate = startDate.plusMonths(1).minusDays(1);
//		System.out.println(endDate);
//
//		int satAndSunCount = 0;
//
//		while (!startDate.isAfter(endDate)) {
//			DayOfWeek dayOfWeek = startDate.getDayOfWeek();
//			if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
//				satAndSunCount++;
//			}
//			startDate = startDate.plusDays(1);
//		}
//
//		System.out.println(satAndSunCount);
//
////		if (currentDate.equals(endDate))
//		if (true) {
//			jdbcTemplate.update(" CREATE  TABLE my_half  AS  \n" + " SELECT emp_id, SUM(half_day) AS half_days \n"
//					+ "	FROM attendence \n" + "	WHERE MONTH(date) = MONTH(CURRENT_DATE) \n"
//					+ "	AND YEAR(date) = YEAR(CURRENT_DATE) \n" + "	GROUP BY emp_id \n");
//			jdbcTemplate.update(" CREATE TABLE my_full  AS \n"
//					+ " SELECT emp_id , total_days FROM leave_application where total_days>0 \n");
//			jdbcTemplate.update("CREATE TABLE final AS "
//					+ "SELECT my_half.emp_id, my_half.half_days,  COALESCE(my_full.total_days ,0) AS total_days "
//					+ "FROM my_half " + "LEFT JOIN my_full " + "ON my_half.emp_id = my_full.emp_id");
//
//			jdbcTemplate.update(" drop table my_half ");
//			jdbcTemplate.update(" DROP TABLE my_full ");
//			jdbcTemplate.update(" INSERT  INTO  monthly_leave_record  ( full_day_leaves, half_day_leaves ,emp_id) \n "
//					+ " select  COALESCE(f.half_days ,0) , COALESCE(f.total_days,0) ,e.id \n" + " from employee as e \n"
//					+ " left join final as f \n" + " on e.id = f.emp_id \n");
//			jdbcTemplate.update(" DROP TABLE final ");
//			jdbcTemplate.update(" UPDATE monthly_leave_record "
//					+ "SET  total_leaves =  (half_day_leaves*(1.0))/(2.0) + (1.0)*full_day_leaves ,"
//					+ "total_pl =2 , deduction = total_pl - total_leaves ,"
//					+ " extra_leaves =  CASE WHEN  deduction < 0 THEN -deduction ELSE 0 END ,"
//					+ "remaining_pl =  CASE WHEN  deduction >= 0 THEN deduction ELSE 0 END ");
//		}
//
//	}
//}
