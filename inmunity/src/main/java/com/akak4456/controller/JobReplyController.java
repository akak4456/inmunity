package com.akak4456.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akak4456.domain.board.JobBoard;
import com.akak4456.domain.reply.JobReply;

import lombok.extern.java.Log;
@RestController
@Log
@RequestMapping("/jobboard/**")
public class JobReplyController extends ReplyController<JobBoard, JobReply> {@Override
	protected JobBoard makeOneEmptyBoardByBno(Long bno) {
		// TODO Auto-generated method stub
		return JobBoard.builder().title("empty").content("empty").bno(bno).build();
	}

}
