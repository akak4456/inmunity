package com.akak4456.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akak4456.domain.board.NotifyBoard;
import com.akak4456.domain.reply.NotifyReply;

import lombok.extern.java.Log;
@RestController
@Log
@RequestMapping("/notifyboard/**")
public class NotifyReplyController extends ReplyController<NotifyBoard, NotifyReply> {

	@Override
	protected NotifyBoard makeOneEmptyBoardByBno(Long bno) {
		// TODO Auto-generated method stub
		return NotifyBoard.builder().title("empty").content("empty").bno(bno).build();
	}

}
