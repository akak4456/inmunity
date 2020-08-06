package com.akak4456.persistent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.akak4456.domain.board.FreeBoard;
import com.akak4456.inmunity.InmunityApplication;
import com.akak4456.persistent.board.FreeBoardRepository;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmunityApplication.class)
@Log
@Commit
public class FreeBoardInit {
	@Autowired
	private FreeBoardRepository repo;
	
	@Test
	public void makeData() {
		repo.deleteAll();
		for(Long i=0L;i<200L;i++) {
			//insert 100 data;
			FreeBoard board = new FreeBoard();
			board.setTitle(i+"번째 제목");
			board.setContent(i+"번째 내용");
			repo.save(board);
		}
	}
}
