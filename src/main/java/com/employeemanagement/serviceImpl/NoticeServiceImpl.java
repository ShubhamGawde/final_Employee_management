package com.employeemanagement.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeemanagement.Repository.NoticeRepo;
import com.employeemanagement.Request.CreateNoticeRequest;
import com.employeemanagement.entity.Notice;
import com.employeemanagement.exceptionhandler.CustomeException;
import com.employeemanagement.service.NoticeService;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeRepo noticeRepo;

	@Override
	public Notice setNotice(CreateNoticeRequest req) {
		Notice notice = new Notice();
	    System.out.println("hello");

	    notice.setSubject(req.getSubject());
	    notice.setMessage(req.getMessage());
	    System.out.println("hello1");

	    
	    // Parse issue date
	    LocalDate issueDate = LocalDate.parse(req.getIssueDate());
	    notice.setIssueDate(issueDate);
	    System.out.println("hello2");


	    // Parse expiration date
	    LocalDate expirationDate = LocalDate.parse(req.getExpirationDate());
	    notice.setExpirationDate(expirationDate);
	    
	    System.out.println("hello3");

	    return this.noticeRepo.save(notice);
	}

	@Override
	public Notice updateNotice(int noticeId, CreateNoticeRequest newInfo) throws CustomeException {
		Notice notice = this.getNoticeById(noticeId);
		notice.setSubject(newInfo.getSubject());
		notice.setMessage(newInfo.getMessage());
		notice.setIssueDate(LocalDate.parse(newInfo.getIssueDate()));
		notice.setExpirationDate(LocalDate.parse(newInfo.getExpirationDate()));
		return this.noticeRepo.save(notice);
	}

	@Override
	public void deleteNotice(int noticeId) throws CustomeException {

		Notice notice = this.getNoticeById(noticeId);

		this.noticeRepo.deleteById(notice.getId());

	}

	@Override
	public List<Notice> getAllNotice() {

		return this.noticeRepo.findAll();
	}

	@Override
	public Notice getNoticeById(int noticeId) throws CustomeException {
		Optional<Notice> notice = this.noticeRepo.findById(noticeId);
		if (notice.isPresent()) {
			return notice.get();
		}
		throw new CustomeException(false, "Notice not found with id : " + noticeId, 404);
	}

}
