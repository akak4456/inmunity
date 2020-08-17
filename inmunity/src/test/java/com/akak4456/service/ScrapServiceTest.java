package com.akak4456.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.board.FreeBoard;
import com.akak4456.domain.member.EmailCheck;
import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.member.Role;
import com.akak4456.exception.ScrapAlreadyExist;
import com.akak4456.inmunity.InmunityApplication;
import com.akak4456.persistent.BoardRepository;
import com.akak4456.persistent.FileUploadRepository;
import com.akak4456.persistent.MemberRepository;
import com.akak4456.persistent.RecommendOrOppositeRepository;
import com.akak4456.persistent.ReplyRepository;
import com.akak4456.persistent.ScrapRepository;
import com.akak4456.vo.PageVO;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmunityApplication.class)
@Log
@Commit
public class ScrapServiceTest {
	@Autowired
	private BoardRepository boardRepo;
	@Autowired
	private FileUploadRepository fileUploadRepo;
	@Autowired
	private MemberRepository memberRepo; 
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private ReplyRepository replyRepo;
	
	@Autowired
	private RecommendOrOppositeRepository roRepo;
	
	@Autowired
	private ScrapRepository scrapRepo;

	@Autowired
	private ScrapService scrapService;
	
	private MemberEntity member;
	private MemberEntity member2;
	private Board board;
	@Before
	public void setUp() {
		fileUploadRepo.deleteAll();
		replyRepo.deleteAll();
		roRepo.deleteAll();
		scrapRepo.deleteAll();
		boardRepo.deleteAll();
		if(!memberRepo.findById("akak4456@naver.com").isPresent()) {
			member = MemberEntity.builder().useremail("akak4456@naver.com").role(Role.ROLE_MEMBER).name("akak4456").emailCheck(EmailCheck.N).build();
			memberRepo.save(member);
		}else {
			member = memberRepo.findById("akak4456@naver.com").get();
		}
		if(!memberRepo.findById("akak4478@naver.com").isPresent()) {
			member2 = MemberEntity.builder().useremail("akak4478@naver.com").role(Role.ROLE_MEMBER).name("akak4456").emailCheck(EmailCheck.N).build();
			memberRepo.save(member2);
		}else {
			member2 = memberRepo.findById("akak4478@naver.com").get();
		}
		board = FreeBoard.builder()
				.title("title")
				.content("content")
				.member(member)
				.build();

		boardRepo.save(board);
	}
	@Test(expected=ScrapAlreadyExist.class)
	public void addScrapTest() {
		log.info("addScrap query start");
		scrapService.addScrap(member.getUseremail(), board.getBno());
		log.info("addScrap query done");
		assertEquals(scrapRepo.count(),1L);
		log.info("addScrap query start");
		scrapService.addScrap(member2.getUseremail(), board.getBno());
		log.info("addScrap query done");
		assertEquals(scrapRepo.count(),2L);
		scrapService.addScrap(member.getUseremail(), board.getBno());//예외가 발생해야 함
	}
	@Test
	public void getListWithUseremailTest() {
		Pageable pageable = new PageVO(1).makePageble(1,"regdate");
		scrapService.getListWithUseremail("akak4456@naver.com", pageable);
	}
}
