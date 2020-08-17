package com.akak4456.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akak4456.domain.board.FreeBoard;
import com.akak4456.domain.reply.FreeReply;

import lombok.extern.java.Log;
@RestController
@Log
@RequestMapping("/freeboard/**")
public class FreeReplyController extends ReplyController<FreeBoard, FreeReply> {@Override
	protected FreeBoard makeOneEmptyBoardByBno(Long bno) {
		// TODO Auto-generated method stub
		FreeBoard board = FreeBoard.builder().bno(bno).title("empty").content("empty").build();
		return board;
	}

}
