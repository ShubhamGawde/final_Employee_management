package com.employeemanagement.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

//import com.employeemanagement.entity.Address;
import com.employeemanagement.entity.Employee;

@Component
public class ExcelUploadHelper {

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	public boolean checkExcelFormate(MultipartFile file) {

		if (file.getContentType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {

			return true;

		}

		return false;

	}

	public List<Employee> convertExcelToList(InputStream is) throws IOException {

		List<Employee> list = new ArrayList<>();

		XSSFWorkbook workBook = new XSSFWorkbook(is);

		XSSFSheet sheet = workBook.getSheet("data");

		Iterator<Row> rows = sheet.iterator();

		int rowNum = 0;

		while (rows.hasNext()) {

			Row row = rows.next();

			if (rowNum == 0) {

				rowNum++;
				continue;

			}

			Iterator<Cell> cells = row.iterator();

			int cellNum = 0;

			Employee employee = new Employee();

			while (cells.hasNext()) {

				Cell cell = cells.next();

				switch (cellNum) {

				case 0:
					employee.setId((int) cell.getNumericCellValue());
					break;
				case 1:
					employee.setFirstName(cell.getStringCellValue());
					break;
				case 2:
					employee.setLastName(cell.getStringCellValue());
					break;
				case 3:
					employee.setEmail(cell.getStringCellValue());
					break;
				case 4:
					employee.setPassword(passwordEncoder.encode(cell.getStringCellValue()));
					break;
				case 5:
//					employee.setSalary(cell.getNumericCellValue());
					break;
				case 6:
					employee.setPhone(cell.getStringCellValue());
					break;
				case 7:
					employee.setAlterPhone(cell.getStringCellValue());
					break;
				case 8:
					employee.setDob(cell.getStringCellValue());
					break;
				case 9:
					employee.setExperience(cell.getStringCellValue());
					break;
				case 10:
					employee.setCollege(cell.getStringCellValue());
					break;
				case 11:
					employee.setGender(cell.getStringCellValue());
					break;
				case 12:
					employee.setMaritalStatus(cell.getStringCellValue());
					break;
				case 13:
					employee.setJobTitle(cell.getStringCellValue());
					break;
				case 14:
					employee.setStream(cell.getStringCellValue());
					break;
				case 15:
					employee.setUniversity(cell.getStringCellValue());
					break;
				case 16:
					employee.setJoiningDate(cell.getStringCellValue());
					break;
				case 17:
					employee.setQualification(cell.getStringCellValue());
					break;
				case 18:
					employee.setProfileImgUrl(null);
					break;
				case 19:
					employee.setType(cell.getStringCellValue());
					break;
				case 20:
					employee.setWorkLocation(cell.getStringCellValue());
					break;
				case 21:
//					Address addr = new Address();
					employee.setAddress(null);
					break;
				default:
					break;
				}
				employee.setRole("EMPLOYEE");
				employee.setEnabled(false);
				employee.setDeleted(false);
				employee.setActive(false);

				cellNum++;

				list.add(employee);

			}

		}

		workBook.close();

		return list;

	}

}
