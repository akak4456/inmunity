package com.akak4456.persistent.mylog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.akak4456.inmunity.InmunityApplication;
import com.akak4456.persistent.boardandreplygeneral.BoardGeneralRepository;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmunityApplication.class)
@Log
@Commit
public class BoardMyLogTest {
	@Autowired
	private BoardGeneralRepository repo;
	
	@Test
	public void findAllTest() {
		repo.findAll();
	}
}
