package com.akak4456.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akak4456.domain.board.GraduateBoard;
import com.akak4456.domain.reply.GraduateReply;

import lombok.extern.java.Log;

@RestController
@Log
@RequestMapping("/graduateboard/**")
public class GraduateReplyController extends ReplyController<GraduateBoard, GraduateReply> {

	@Override
	protected GraduateBoard makeOneEmptyBoardByBno(Long bno) {
		// TODO Auto-generated method stub
		GraduateBoard board = GraduateBoard.builder().bno(bno).title("empty").content("empty").build();
		return board;
	}

}
