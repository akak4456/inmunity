package com.akak4456.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.reply.Reply;
import com.akak4456.persistent.board.BoardRepository;
import com.akak4456.persistent.reply.ReplyRepository;
@Service
public class LogServiceImpl implements LogService {
	@Autowired
	private BoardRepository<Board> boardRepo;
	@Autowired
	private ReplyRepository<Reply> replyRepo;
	@Override
	public Page<Board> getBoardListWithUseremail(String useremail, Pageable pageable) {
		// TODO Auto-generated method stub
		return boardRepo.findAllByUseremail(useremail, pageable);
	}

	@Override
	public Page<Reply> getReplyListWithUseremail(String useremail, Pageable pageable) {
		// TODO Auto-generated method stub
		return replyRepo.findAllByUseremail(useremail, pageable);
	}

}
