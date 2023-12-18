package com.employeemanagement.service;

import java.util.List;

import com.employeemanagement.Request.CreateNoticeRequest;
import com.employeemanagement.entity.Notice;
import com.employeemanagement.exceptionhandler.CustomeException;

public interface NoticeService {

	public Notice setNotice(CreateNoticeRequest req);

	public Notice updateNotice(int noticeId, CreateNoticeRequest newInfo) throws CustomeException;

	public void deleteNotice(int noticeId) throws CustomeException;

	public List<Notice> getAllNotice();

	public Notice getNoticeById(int noticeId) throws CustomeException;

}
