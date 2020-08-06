package com.akak4456.persistent;

import org.junit.Before;
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
public class FreeBoardRepositoryTest {
	@Autowired
	private FreeBoardRepository repo;
	@Test
	public void init() {
		log.info(repo.count()+"개의 게시물이 있음");
	}
}
