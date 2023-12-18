package com.employeemanagement.service;

import java.util.List;

import com.employeemanagement.entity.AttendenceRules;
import com.employeemanagement.exceptionhandler.CustomeException;

public interface AttendenceRuleService {

	public AttendenceRules addRule(AttendenceRules atr);

	public void deleteRule(int atr_id) throws CustomeException;

	public AttendenceRules updateRule(int atr_id, AttendenceRules atr) throws CustomeException;

	public AttendenceRules getRuleById(int atr_id) throws CustomeException;

	public List<AttendenceRules> getAllRule();

}
