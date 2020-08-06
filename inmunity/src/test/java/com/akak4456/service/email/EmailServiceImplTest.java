package com.akak4456.service.email;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.akak4456.inmunity.InmunityApplication;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmunityApplication.class)
@Log
@Commit
public class EmailServiceImplTest {
	@Autowired
	private EmailServiceImpl emailService;
	@Test
	public void emailSendTest() {
		emailService.sendMail( "akak4456@naver.com","title" , "<h1>you are welcome!</h1>");
	}
}
