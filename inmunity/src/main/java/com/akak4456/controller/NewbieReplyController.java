package com.akak4456.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akak4456.domain.board.NewbieBoard;
import com.akak4456.domain.reply.NewbieReply;

import lombok.extern.java.Log;
@RestController
@Log
@RequestMapping("/newbieboard/**")
public class NewbieReplyController extends ReplyController<NewbieBoard, NewbieReply> {@Override
	protected NewbieBoard makeOneEmptyBoardByBno(Long bno) {
		// TODO Auto-generated method stub
		return NewbieBoard.builder().title("empty").content("empty").bno(bno).build();
	}

}
