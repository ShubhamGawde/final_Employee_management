package com.employeemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.Response.Response;
import com.employeemanagement.entity.AttendenceRules;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.service.AttendenceRuleService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AttendeceRuleController {
	@Autowired
	private AttendenceRuleService attendenceRuleService;

	@PostMapping("/admin/add_attendance_rule/")
	public ResponseEntity<Response> addRule(@RequestBody AttendenceRules rule) {
		AttendenceRules addRule = this.attendenceRuleService.addRule(rule);

		return new ResponseEntity<>(new Response(true, "Rule created succesfully", addRule), HttpStatus.OK);
	}

	@PostMapping("/admin/update_rule/{atr_id}")
	public ResponseEntity<Response> updateRule(@PathVariable("atr_id") int atr_id, @RequestBody AttendenceRules rule)
			throws CustomeException {
		AttendenceRules updatedRule = this.attendenceRuleService.updateRule(atr_id, rule);

		return new ResponseEntity<>(new Response(true, "updated successfully", updatedRule), HttpStatus.OK);
	}

	@DeleteMapping("/admin/delete_rule/{atr_id}")
	public ResponseEntity<String> deleteRule(@PathVariable("atr_id") int atr_id) throws CustomeException {
		this.attendenceRuleService.deleteRule(atr_id);

		return ResponseEntity.ok("Rule deleted successfully");
	}

	@GetMapping("/get_rules")
	public ResponseEntity<List<AttendenceRules>> getAllRule() {
		List<AttendenceRules> allRule = this.attendenceRuleService.getAllRule();

		return ResponseEntity.ok(allRule);
	}

}
