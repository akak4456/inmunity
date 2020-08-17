package com.akak4456.persistent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.akak4456.domain.board.FreeBoard;
import com.akak4456.domain.member.EmailCheck;
import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.member.Role;
import com.akak4456.domain.reply.FreeReply;
import com.akak4456.domain.reply.Reply;
import com.akak4456.inmunity.InmunityApplication;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmunityApplication.class)
@Log
@Commit
public class ReplyRepositoryTest {
	@Autowired
	private MemberRepository memberRepo;
	private MemberEntity member;
	@Before
	public void setUp() {
		if(!memberRepo.existsById("akak4456@naver.com")) {
			member = MemberEntity.builder()
					.useremail("akak4456@naver.com")
					.name("akak4456")
					.role(Role.ROLE_MEMBER)
					.emailCheck(EmailCheck.N).build();
			memberRepo.save(member);
		}else {
			member = memberRepo.findById("akak4456@naver.com").get();
		}
	}
	@Autowired
	private BoardRepository boardRepo;
	@Autowired
	private ReplyRepository replyRepo;
	
	@Test
	public void changeToDeleteByUseremailTest() {
		FreeBoard board = FreeBoard.builder()
							.title("title")
							.content("content")
							.member(member).build();
		boardRepo.save(board);
		
		FreeReply reply = FreeReply.builder()
							.reply("reply")
							.member(member).build();
		
		replyRepo.save(reply);
		
		Reply getReply1 = replyRepo.findById(reply.getRno()).get();
		assertEquals(getReply1.getMember().getUseremail(),"akak4456@naver.com");
		log.info("changeToDeleteByUseremail query start");
		replyRepo.changeToDeleteByUseremail("akak4456@naver.com");
		log.info("changeToDeleteByUseremail query end");
		Reply getReply2 = replyRepo.findById(reply.getRno()).get();
		assertNull(getReply2.getMember());
	}

}
