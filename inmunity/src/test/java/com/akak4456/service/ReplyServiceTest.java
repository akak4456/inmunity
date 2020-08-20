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
import com.akak4456.domain.fileupload.BoardFileUpload;
import com.akak4456.domain.member.EmailCheck;
import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.member.Role;
import com.akak4456.domain.reply.Reply;
import com.akak4456.inmunity.InmunityApplication;
import com.akak4456.persistent.FileUploadRepository;
import com.akak4456.persistent.MemberRepository;
import com.akak4456.persistent.RecommendOrOppositeRepository;
import com.akak4456.persistent.ScrapRepository;
import com.akak4456.persistent.board.BoardRepository;
import com.akak4456.persistent.reply.ReplyRepository;
import com.akak4456.service.reply.ReplyService;
import com.akak4456.vo.PageVO;

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmunityApplication.class)
@Log
@Commit
public abstract class ReplyServiceTest<B extends Board, R extends Reply> {
	@Autowired
	private ReplyService<R> replyService;
	
	@Autowired
	private ReplyRepository<R> replyRepo;
	
	@Autowired
	private MemberRepository memberRepo;
	
	@Autowired
	private BoardRepository<B> boardRepo;
	
	@Autowired
	private RecommendOrOppositeRepository roRepo;
	
	@Autowired
	private ScrapRepository scrapRepo;
	
	@Autowired
	private FileUploadRepository fileUploadRepo;
	
	private MemberEntity member;
	
	private B board;
	
	protected abstract B makeOneBoard(String title,String content,MemberEntity member,List<BoardFileUpload> boardFileUpload);
	protected abstract R makeOneReply(String reply,MemberEntity member,B board,Long parent_rno);
	
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
		board = makeOneBoard("title","content",member,null);
		boardRepo.save(board);
	}
	@Test
	public void addReplyTest() {
		//equalCount(board,0);
		R reply1 = makeOneReply("reply",member,board,-1L);
		log.info("addReply query start");
		replyService.addReply(reply1);
		log.info("addReply query done");
		//equalCount(board,1);
		R reply2 = makeOneReply("reply2",member,board,-1L);
		log.info("addReply query start");
		replyService.addReply(reply2);
		log.info("addReply query done");
		//equalCount(board,2);
		R reply3 = makeOneReply("reply3",member,board,-1L);
		log.info("addReply query start");
		replyService.addReply(reply3);
		log.info("addReply query done");
		//equalCount(board,3);
	}
	@Test
	public void updateTest() {
		R reply1 = makeOneReply("reply1",member,board,-1L);
		replyService.addReply(reply1);
		reply1.setReply("new reply");
		log.info("updateReply query start");
		replyService.updateReply(reply1.getRno(),reply1);
		log.info("updateReply query done");
		R updatedReply = replyRepo.findById(reply1.getRno()).get();
		assertEquals(updatedReply.getReply(),"new reply");
	}
	
	@Test
	public void deleteTest() {
		R reply = makeOneReply("reply",member,board,-1L);
		replyService.addReply(reply);
		assertEquals(replyRepo.count(),1);
		log.info("deleteReply query start");
		replyService.deleteReply(reply.getRno());
		log.info("deleteReply query done");
		assertEquals(replyRepo.count(),1);
		R deleteReply = replyRepo.findById(reply.getRno()).get();
		assertEquals(deleteReply.getReply(),"삭제된 내용");
	}
	
	@Test
	public void hierarchicaltest() {
		R reply1 = makeOneReply("reply1",member,board,-1L);
		replyService.addReply(reply1);
		R reply1_1 = makeOneReply("reply1_1",member,board,reply1.getRno());
		replyService.addReply(reply1_1);
		R reply1_2 = makeOneReply("reply1_2",member,board,reply1.getRno());
		replyService.addReply(reply1_2);
		R reply1_3 = makeOneReply("reply1_3",member,board,reply1.getRno());
		replyService.addReply(reply1_3);
		
		R reply2 = makeOneReply("reply2",member,board,-1L);
		replyService.addReply(reply2);
		R reply2_1 = makeOneReply("reply2_1",member,board,reply2.getRno());
		replyService.addReply(reply2_1);
		R reply1_4 = makeOneReply("reply1_4",member,board,reply1.getRno());
		replyService.addReply(reply1_4);
		R reply2_2 = makeOneReply("reply2_2",member,board,reply2.getRno());
		replyService.addReply(reply2_2);
		
		R reply3 = makeOneReply("reply3",member,board,-1L);
		replyService.addReply(reply3);
		R reply3_1 = makeOneReply("reply3_1",member,board,reply3.getRno());
		replyService.addReply(reply3_1);
		R reply2_3 = makeOneReply("reply2_3",member,board,reply2.getRno());
		replyService.addReply(reply2_3);
		R reply3_2 = makeOneReply("reply3_2",member,board,reply3.getRno());
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
		Page<R> page = replyService.getListWithPaging(board.getBno(), pageable);
		log.info("getListWithPaging query done");
		
		
		List<R> replies = page.getContent();
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
