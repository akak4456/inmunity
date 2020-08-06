package com.akak4456.service.member;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.akak4456.inmunity.InmunityApplication;
import com.akak4456.persistent.member.MemberRepository;
import com.akak4456.vo.MemberVO;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmunityApplication.class)
@Log
@Commit
public class MemberServiceTest {
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Test
	public void joinTest() {
		memberRepo.deleteAll();
		assertEquals(memberRepo.count(),0);
		MemberVO member = new MemberVO();
		member.setUseremail("email@email.com");
		member.setUserid("akak4456");
		member.setUserpw("1234");
		memberService.joinPost(member);
		assertEquals(memberRepo.count(),1);
	}
}
