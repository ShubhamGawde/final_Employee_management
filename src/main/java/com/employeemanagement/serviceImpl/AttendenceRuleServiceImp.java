package com.employeemanagement.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeemanagement.Repository.AttendeceRuleRepo;
import com.employeemanagement.entity.AttendenceRules;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.service.AttendenceRuleService;

@Service
public class AttendenceRuleServiceImp implements AttendenceRuleService {

	@Autowired
	private AttendeceRuleRepo ruleRepo;

	@Override
	public AttendenceRules addRule(AttendenceRules atr) {

		return this.ruleRepo.save(atr);

	}

	@Override
	public void deleteRule(int atr_id) throws CustomeException {
		AttendenceRules ruleById = this.getRuleById(atr_id);

		this.ruleRepo.delete(ruleById);

	}

	@Override
	public AttendenceRules updateRule(int atr_id, AttendenceRules atr) throws CustomeException {

		AttendenceRules ruleById = this.getRuleById(atr_id);
		ruleById.setName(atr.getName());
		ruleById.setDescription(atr.getDescription());
		this.ruleRepo.save(ruleById);
		return ruleById;
	}

	@Override
	public AttendenceRules getRuleById(int atr_id) throws CustomeException {
		Optional<AttendenceRules> rule = this.ruleRepo.findById(atr_id);
		if (rule.isPresent()) {

			return rule.get();
		}

		throw new CustomeException(false, " rule not found with id :" + atr_id, 404);
	}

	@Override
	public List<AttendenceRules> getAllRule() {
		return this.ruleRepo.findAll();
	}

}
