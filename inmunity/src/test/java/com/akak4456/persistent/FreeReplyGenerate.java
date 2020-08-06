package com.akak4456.persistent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.akak4456.domain.board.FreeBoard;
import com.akak4456.domain.reply.FreeReply;
import com.akak4456.inmunity.InmunityApplication;
import com.akak4456.persistent.board.FreeBoardRepository;
import com.akak4456.service.reply.FreeReplyServiceImpl;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmunityApplication.class)
@Log
@Commit
public class FreeReplyGenerate {
	@Autowired
	private FreeBoardRepository boardRepo;
	@Autowired
	private FreeReplyServiceImpl replyService;
	
	@Test
	public void generateData() {
		boardRepo.deleteAll();
		FreeBoard board =  new FreeBoard();
		board.setTitle("TITLE");
		board.setContent("CONTENT");
		boardRepo.save(board);
		for(int i=0;i<300;i++) {
			FreeReply reply = new FreeReply();
			reply.setBoard(board);
			reply.setParent_rno(-1L);
			reply.setReply("reply"+i);
			reply.setReplyer("replyer"+i);
			replyService.addReply(reply);
		}
	}
}
