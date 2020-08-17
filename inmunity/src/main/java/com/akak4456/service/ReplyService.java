package com.akak4456.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.akak4456.domain.reply.Reply;

public interface ReplyService {
	public void addReply(Reply reply);
	
	public void updateReply(Long rno,Reply reply);
	
	public Page<Reply> getListWithPaging(Long bno,Pageable pageable);
	
	public void deleteReply(Long rno);
	
	public Page<Reply> getListWithUseremail(String useremail,Pageable pageable);
}
