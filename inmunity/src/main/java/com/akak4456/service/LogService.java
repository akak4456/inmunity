package com.akak4456.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.reply.Reply;

public interface LogService {
	Page<Board> getBoardListWithUseremail(String useremail,Pageable pageable);
	
	Page<Reply> getReplyListWithUseremail(String useremail,Pageable pageable);
}
