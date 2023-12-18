package com.employeemanagement.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeemanagement.Repository.LeaveTypeRepo;

import com.employeemanagement.entity.LeaveType;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.service.LeaveTypeService;

@Service
public class LeaveTypeServiceImpl implements LeaveTypeService {

	@Autowired
	private LeaveTypeRepo leaveTypeRepo;

	@Override
	public LeaveType addNewLeaveType(LeaveType leaveType) {

		LeaveType savedLeaveType = this.leaveTypeRepo.save(leaveType);

		return savedLeaveType;

	}

	@Override
	public LeaveType updateLeaveType(int leaveType_id, LeaveType leaveType) throws CustomeException {

//		LeaveType leaveTypeById = this.getLeaveTypeById(leaveType_id);
//		if (leaveTypeById.getId() == leaveType.getId()) {
//
//			leaveTypeById.setNumberOfDays(leaveType.getNumberOfDays());
//			leaveTypeById.setType(leaveType.getType());
//			LeaveType save = this.leaveTypeRepo.save(leaveTypeById);
//
//			List<Employee> allEmployee = this.employeeService.getAllEmployee();
//
//			for (Employee emp : allEmployee) {
//
//				emp.getAllotedLeave().add(allotedLeave);
//				employeeRepo.save(emp);
//
//			}
//			return save;
//
//		}
//
//		throw new LeaveTypeException("leave type and id both are different");

		return null;
	}

	@Override
	public void removeLeaveType(int leaveType_id) throws CustomeException {
		this.getLeaveTypeById(leaveType_id);
		this.leaveTypeRepo.deleteById(leaveType_id);

	}

	@Override
	public List<LeaveType> getAllLeaveType() {

		return this.leaveTypeRepo.findAll();
	}

	@Override
	public LeaveType getLeaveTypeById(int leaveType_id) throws CustomeException {
		Optional<LeaveType> leaveType = this.leaveTypeRepo.findById(leaveType_id);
		if (leaveType.isPresent()) {
			return leaveType.get();
		}
		throw new CustomeException(false, "leave not present with this id : " + leaveType_id, 404);
	}

}
