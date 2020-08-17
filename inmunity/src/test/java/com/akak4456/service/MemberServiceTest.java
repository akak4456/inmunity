package com.akak4456.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.akak4456.domain.board.FreeBoard;
import com.akak4456.domain.fileupload.BoardFileUpload;
import com.akak4456.domain.member.EmailCheck;
import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.recommendoropposite.RecommendOrOpposite;
import com.akak4456.domain.recommendoropposite.RecommendOrOppositeEnum;
import com.akak4456.domain.recommendoropposite.RecommendOrOppositeId;
import com.akak4456.domain.reply.FreeReply;
import com.akak4456.domain.scrap.Scrap;
import com.akak4456.domain.scrap.ScrapId;
import com.akak4456.inmunity.InmunityApplication;
import com.akak4456.persistent.BoardRepository;
import com.akak4456.persistent.FileUploadRepository;
import com.akak4456.persistent.MemberRepository;
import com.akak4456.persistent.RecommendOrOppositeRepository;
import com.akak4456.persistent.ReplyRepository;
import com.akak4456.persistent.ScrapRepository;
import com.akak4456.service.member.MemberService;
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
	private BoardRepository boardRepo;
	@Autowired
	private FileUploadRepository fileUploadRepo;
	@Autowired
	private MemberRepository memberRepo; 
	@Autowired
	private ReplyRepository replyRepo;
	@Autowired
	private RecommendOrOppositeRepository roRepo;
	@Autowired
	private ScrapRepository scrapRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Before
	public void setUp() {
		fileUploadRepo.deleteAll();
		replyRepo.deleteAll();
		roRepo.deleteAll();
		scrapRepo.deleteAll();
		boardRepo.deleteAll();
		memberRepo.deleteAll();
	}
	@Test
	public void generalCaseTest() {
		MemberVO memberVO = new MemberVO();
		memberVO.setUseremail("akak4456@naver.com");
		memberVO.setUserid("akak4456");
		memberVO.setUserpw("1234");
		
		log.info("joinPost query start");
		memberService.joinPost(memberVO);
		log.info("joinPost query done");
		
		log.info("isSamdId query start");
		assertFalse(memberService.isSameId("akak4478"));
		log.info("isSamdId query done");
		log.info("isSamdId query start");
		assertTrue(memberService.isSameId("akak4456"));
		log.info("isSamdId query done");
		
		log.info("isSamdEmail query start");
		assertFalse(memberService.isSameEmail("akak4478@naver.com"));
		log.info("isSamdEmail query done");
		log.info("isSamdEmail query start");
		assertTrue(memberService.isSameEmail("akak4456@naver.com"));
		log.info("isSamdEmail query done");
		
		
		log.info("updateAuthKey query start");
		memberService.updateAuthKey("aaaa", "akak4456");
		log.info("updateAuthKey query done");
		assertEquals(memberRepo.findById("akak4456@naver.com").get().getAuthKey(),"aaaa");
		
		log.info("checkEmailCode query start");
		assertTrue(memberService.checkEmailCode("aaaa", "akak4456"));
		log.info("checkEmailCode query done");
		log.info("checkEmailCode query start");
		assertFalse(memberService.checkEmailCode("bbbb", "akak4456"));
		log.info("checkEmailCode query done");
		
		log.info("isEmailAuthenticated query start");
		assertFalse(memberService.isEmailAuthenticated("akak4456@naver.com"));
		log.info("isEmailAuthenticated query done");
		log.info("updateEmailCheck query start");
		memberService.updateEmailCheck(EmailCheck.Y,"akak4456");
		log.info("updateEmailCheck query done");
		
		log.info("isEmailAuthenticated query start");
		assertTrue(memberService.isEmailAuthenticated("akak4456@naver.com"));
		log.info("isEmailAuthenticated query done");
		
		log.info("isMatchUseremailAndPassword query start");
		assertTrue(memberService.isMatchUseremailAndPassword("akak4456@naver.com", "1234"));
		log.info("isMatchUseremailAndPassword query done");
		
		log.info("changePw query start");
		memberService.changePw("akak4456@naver.com", "5678");
		log.info("changePw query done");
		
		log.info("isMatchUseremailAndPassword query start");
		assertFalse(memberService.isMatchUseremailAndPassword("akak4456@naver.com", "1234"));
		log.info("isMatchUseremailAndPassword query done");
		
		log.info("isMatchUseremailAndPassword query start");
		assertTrue(memberService.isMatchUseremailAndPassword("akak4456@naver.com", "5678"));
		log.info("isMatchUseremailAndPassword query done");
		
		assertEquals(memberRepo.findById("akak4456@naver.com").get().getName(),"akak4456");//처음 아이디를 만들 때 아이디와 네임은 같음
		
		log.info("changeInfo query start");
		memberService.changeInfo("akak4456@naver.com", "nanbaboda");
		log.info("changeInfo query done");
		
		log.info("changeProfile query start");
		memberService.changeProfile("abc", "akak4456@naver.com");
		log.info("changeProfile query done");
		
		MemberEntity member = memberRepo.findById("akak4456@naver.com").get();
		List<BoardFileUpload> boardFileUpload = new ArrayList<>();
		boardFileUpload.add(BoardFileUpload.builder().uploadPath("2020/01/01").uploadFileName("name").build());
		boardFileUpload.add(BoardFileUpload.builder().uploadPath("2020/01/01").uploadFileName("name2").build());
		FreeBoard board = FreeBoard.builder()
							.title("title")
							.content("content")
							.member(member)
							.fileUpload(boardFileUpload).build();
		boardRepo.save(board);
		FreeReply reply = FreeReply.builder().reply("reply").member(member).board(board).build();
		replyRepo.save(reply);
		RecommendOrOppositeId id1 = new RecommendOrOppositeId(board.getBno(),member.getUseremail());
		RecommendOrOpposite ro = RecommendOrOpposite.builder().id(id1).board(board).member(member).recommendOrOpposite(RecommendOrOppositeEnum.Recommend).build();
		roRepo.save(ro);
		ScrapId id2 = new ScrapId(board.getBno(),member.getUseremail());
		Scrap scrap = Scrap.builder().id(id2).board(board).member(member).build();
		scrapRepo.save(scrap);
		assertEquals(boardRepo.count(),1L);
		assertEquals(replyRepo.count(),1L);
		assertEquals(roRepo.count(),1L);
		assertEquals(scrapRepo.count(),1L);
		
		log.info("withdrawal query start");
		memberService.withdrawal("akak4456@naver.com");
		log.info("withdrawal query done");
		
		assertNull(boardRepo.findById(board.getBno()).get().getMember());
		assertNull(replyRepo.findById(reply.getRno()).get().getMember());
		assertEquals(boardRepo.count(),1L);
		assertEquals(replyRepo.count(),1L);
		assertEquals(roRepo.count(),0L);
		assertEquals(scrapRepo.count(),0L);
		assertEquals(memberRepo.count(),0);
	}
}
