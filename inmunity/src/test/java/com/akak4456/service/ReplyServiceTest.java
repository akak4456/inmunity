package com.akak4456.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.board.FreeBoard;
import com.akak4456.domain.member.EmailCheck;
import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.member.Role;
import com.akak4456.domain.reply.FreeReply;
import com.akak4456.domain.reply.Reply;
import com.akak4456.inmunity.InmunityApplication;
import com.akak4456.persistent.BoardRepository;
import com.akak4456.persistent.FileUploadRepository;
import com.akak4456.persistent.MemberRepository;
import com.akak4456.persistent.RecommendOrOppositeRepository;
import com.akak4456.persistent.ReplyRepository;
import com.akak4456.persistent.ScrapRepository;
import com.akak4456.vo.PageMaker;
import com.akak4456.vo.PageVO;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmunityApplication.class)
@Log
@Commit
public class ReplyServiceTest {
	@Autowired
	private ReplyService replyService;
	
	@Autowired
	private ReplyRepository replyRepo;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private RecommendOrOppositeRepository roRepo;
	
	@Autowired
	private ScrapRepository scrapRepo;
	
	@Autowired
	private FileUploadRepository fileUploadRepo;
	
	private MemberEntity member;
	
	private FreeBoard board;
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
		board = FreeBoard.builder()
				.title("title" )
				.content("content" )
				.member(member)
				.build();
		boardRepo.save(board);
	}
	@Test
	public void addReplyTest() {
		//equalCount(board,0);
		FreeReply reply1 = FreeReply.builder().board(board).member(member).reply("reply1").parent_rno(-1L).build();
		log.info("addReply query start");
		replyService.addReply(reply1);
		log.info("addReply query done");
		//equalCount(board,1);
		FreeReply reply2 = FreeReply.builder().board(board).member(member).reply("reply2").parent_rno(-1L).build();
		log.info("addReply query start");
		replyService.addReply(reply2);
		log.info("addReply query done");
		//equalCount(board,2);
		FreeReply reply3 = FreeReply.builder().board(board).member(member).reply("reply3").parent_rno(-1L).build();
		log.info("addReply query start");
		replyService.addReply(reply3);
		log.info("addReply query done");
		//equalCount(board,3);
	}
	@Test
	public void updateTest() {
		FreeReply reply1 = FreeReply.builder().board(board).member(member).reply("reply1").parent_rno(-1L).build();
		replyService.addReply(reply1);
		reply1.setReply("new reply");
		log.info("updateReply query start");
		replyService.updateReply(reply1.getRno(),reply1);
		log.info("updateReply query done");
		Reply updatedReply = replyRepo.findById(reply1.getRno()).get();
		assertEquals(updatedReply.getReply(),"new reply");
	}
	
	@Test
	public void deleteTest() {
		FreeReply reply = FreeReply.builder().board(board).member(member).reply("reply1").parent_rno(-1L).build();
		replyService.addReply(reply);
		assertEquals(replyRepo.count(),1);
		log.info("deleteReply query start");
		replyService.deleteReply(reply.getRno());
		log.info("deleteReply query done");
		assertEquals(replyRepo.count(),1);
		Reply deleteReply = replyRepo.findById(reply.getRno()).get();
		assertEquals(deleteReply.getReply(),"삭제된 내용");
	}
	
	private Reply makeOneReply(Long parent_rno,String reply) {
		return FreeReply.builder().board(board).member(member).reply(reply).parent_rno(parent_rno).build();
	}
	
	@Test
	public void hierarchicaltest() {
		Reply reply1 = makeOneReply(-1L,"reply1");
		replyService.addReply(reply1);
		Reply reply1_1 = makeOneReply(reply1.getRno(),"reply1_1");
		replyService.addReply(reply1_1);
		Reply reply1_2 = makeOneReply(reply1.getRno(),"reply1_2");
		replyService.addReply(reply1_2);
		Reply reply1_3 = makeOneReply(reply1.getRno(),"reply1_3");
		replyService.addReply(reply1_3);
		
		Reply reply2 = makeOneReply(-1L,"reply2");
		replyService.addReply(reply2);
		Reply reply2_1 = makeOneReply(reply2.getRno(),"reply2_1");
		replyService.addReply(reply2_1);
		Reply reply1_4 = makeOneReply(reply1.getRno(),"reply1_4");
		replyService.addReply(reply1_4);
		Reply reply2_2 = makeOneReply(reply2.getRno(),"reply2_2");
		replyService.addReply(reply2_2);
		
		Reply reply3 = makeOneReply(-1L,"reply3");
		replyService.addReply(reply3);
		Reply reply3_1 = makeOneReply(reply3.getRno(),"reply3_1");
		replyService.addReply(reply3_1);
		Reply reply2_3 = makeOneReply(reply2.getRno(),"reply2_3");
		replyService.addReply(reply2_3);
		Reply reply3_2 = makeOneReply(reply3.getRno(),"reply3_2");
		replyService.addReply(reply3_2);
		/*
		 reply는 아래와 같아야 함
		 페이지1
		 reply1
		 reply1_1
		 reply1_2
		 reply1_3
		 reply1_4
		 reply2
		 reply2_1
		 reply2_2
		 reply2_3
		 reply3
		 페이지2
		 reply3_1
		 reply3_2
		 */
		assertEquals(replyRepo.count(),12);
		
		Pageable pageable = new PageVO(1).makePageble(1,"path");
		//첫번째 페이지 조사
		log.info("getListWithPaging query start");
		Page<Reply> page = replyService.getListWithPaging(board.getBno(), pageable);
		log.info("getListWithPaging query done");
		
		
		List<Reply> replies = page.getContent();
		assertEquals(replies.size(),10);
		/*
		 * assertEquals(replies.get(0),(Object)reply1);
		 * assertEquals(replies.get(1).getReply(),"reply1_1");
		 * assertEquals(replies.get(2).getReply(),"reply1_2");
		 * assertEquals(replies.get(3).getReply(),"reply1_3");
		 * assertEquals(replies.get(4).getReply(),"reply1_4");
		 * assertEquals(replies.get(5).getReply(),"reply2");
		 * assertEquals(replies.get(6).getReply(),"reply2_1");
		 * assertEquals(replies.get(7).getReply(),"reply2_2");
		 * assertEquals(replies.get(8).getReply(),"reply2_3");
		 * assertEquals(replies.get(9).getReply(),"reply3");
		 */
		
		pageable = new PageVO(2).makePageble(1,"path");
		//두번째 페이지 조사
		log.info("getListWithPaging query start");
		page = replyService.getListWithPaging(board.getBno(), pageable);
		log.info("getListWithPaging query done");
		replies = page.getContent();
		assertEquals(replies.size(),2);
		/*
		 * assertEquals(replies.get(0).getReply(),"reply3_1");
		 * assertEquals(replies.get(1).getReply(),"reply3_2");
		 */
	}
}
