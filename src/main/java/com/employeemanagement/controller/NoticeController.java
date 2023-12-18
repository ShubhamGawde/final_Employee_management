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

import com.employeemanagement.Request.CreateNoticeRequest;
import com.employeemanagement.Response.Response;
import com.employeemanagement.entity.Notice;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.service.NoticeService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;

	@PostMapping("/admin/create_notice")
	public ResponseEntity<Response> createNotice(@RequestBody CreateNoticeRequest req) {

		System.out.println(req);

		Notice createdNotice = this.noticeService.setNotice(req);

		return new ResponseEntity<>(new Response(true, "Notice Created", createdNotice), HttpStatus.CREATED);

	}

	@PostMapping("/admin/update_notice/{notice_id}")
	public ResponseEntity<Response> updateNotice(@PathVariable("notice_id") int notice_id,
			@RequestBody CreateNoticeRequest req) throws CustomeException {

		Notice updatedNotice = this.noticeService.updateNotice(notice_id, req);
		return new ResponseEntity<>(new Response(true, "Notice Updated", updatedNotice), HttpStatus.OK);
	}

	@DeleteMapping("/admin/delete_notice/{notice_id}")
	public ResponseEntity<String> deleteNotice(@PathVariable("notice_id") int notice_id) throws CustomeException {

		this.noticeService.deleteNotice(notice_id);

		return ResponseEntity.ok("Notice deleted");

	}

	@GetMapping("/notices")
	public ResponseEntity<List<Notice>> getAllNotice() {

		List<Notice> allNotice = this.noticeService.getAllNotice();

		return ResponseEntity.ok(allNotice);
	}

	@GetMapping("/notice/{notice_id}")
	public ResponseEntity<Response> getNotice(@PathVariable("notice_id") int notice_id) throws CustomeException {

		Notice noticeById = this.noticeService.getNoticeById(notice_id);

		return new ResponseEntity<>(new Response(true, "Notice ", noticeById), HttpStatus.OK);
	}
}
