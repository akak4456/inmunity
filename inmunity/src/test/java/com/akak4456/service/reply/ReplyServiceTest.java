package com.akak4456.service.reply;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.recommendoropposite.RecommendOrOppositeEntity;
import com.akak4456.domain.reply.Reply;
import com.akak4456.inmunity.InmunityApplication;
import com.akak4456.persistent.board.BoardRepository;
import com.akak4456.persistent.reply.ReplyRepository;
import com.akak4456.service.board.BoardService;
import com.akak4456.vo.PageMaker;
import com.akak4456.vo.PageVO;

import lombok.extern.java.Log;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmunityApplication.class)
@Log
@Commit
public abstract class ReplyServiceTest <B extends Board,R extends Reply, O extends RecommendOrOppositeEntity> {
	@Autowired
	protected BoardRepository<B> boardRepo;
	@Autowired
	protected ReplyRepository<R> replyRepo;
	@Autowired
	protected BoardService<B,O> boardService;
	@Autowired
	protected ReplyService<B,R> replyService;
	
	public abstract B makeOneBoard(String title,String content);
	
	public abstract R makeOneReply(B board,Long parent_rno,String replyer,String reply);
	
	public B findByBoardBno(Long bno) {
		return boardRepo.findById(bno).get();
	}
	@Test
	public void addReplyTest() {
		log.info("addReplyTest start");
		boardRepo.deleteAll();
		replyRepo.deleteAll();
		B board1 = makeOneBoard("reply title","reply content");
		boardService.addBoard(board1);
		equalCount(board1,0);
		R reply1 = makeOneReply(board1,-1L,"user1","reply1");
		log.info("addReply query");
		replyService.addReply(reply1);
		log.info("addReply query done");
		equalCount(board1,1);
		R reply2 = makeOneReply(board1,-1L,"user2","reply2");
		log.info("addReply query");
		replyService.addReply(reply2);
		log.info("addReply query done");
		equalCount(board1,2);
		R reply3 = makeOneReply(board1,-1L,"user3","reply3");
		log.info("addReply query");
		replyService.addReply(reply3);
		log.info("addReply query done");
		equalCount(board1,3);
		B board2 = makeOneBoard("reply title2","reply content2");
		boardService.addBoard(board2);
		equalCount(board2,0);
		R reply4 = makeOneReply(board2,-1L,"user4","reply4");
		log.info("addReply query");
		replyService.addReply(reply4);
		log.info("addReply query done");
		equalCount(board1,3);
		equalCount(board2,1);
		R reply5 = makeOneReply(board2,-1L,"user5","reply5");
		log.info("addReply query");
		replyService.addReply(reply5);
		log.info("addReply query done");
		equalCount(board1,3);
		equalCount(board2,2);
	}
	@Test
	public void updateTest() {
		log.info("updateTest Start");
		B board = makeOneBoard("board","board");
		boardService.addBoard(board);
		R reply = makeOneReply(board,-1L,"user1","reply1");
		replyService.addReply(reply);
		reply.setReply("new reply");
		log.info("updateReply query");
		replyService.updateReply(reply.getRno(),reply);
		log.info("updateReply query done");
		R updatedReply = replyRepo.findById(reply.getRno()).get();
		assertEquals(updatedReply.getReply(),"new reply");
	}
	@Test
	public void getListWithPagingTest() {
		log.info("getListWithPagingTest start");
		boardRepo.deleteAll();
		replyRepo.deleteAll();
		B board = makeOneBoard("board","board");
		boardService.addBoard(board);
		for(int i=0;i<205;i++) {
			R reply = makeOneReply(board,-1L,"user"+i,"reply"+i);
			replyService.addReply(reply);
		}
		assertEquals(replyRepo.count(),205);
		
		Pageable pageable = new PageVO(0).makePageble(0,"path");
		//예외상황
		log.info("getListWithPaging query");
		Page<R> page = replyService.getListWithPaging(board.getBno(), pageable);
		log.info("getListWithPaging query done");
		PageMaker<R> pageMaker = new PageMaker<R>(page);
		pageTest(pageMaker,true,1,10,21,-1,11);
		
		pageable = new PageVO(1).makePageble(0, "path");
		//첫번째 페이지
		log.info("getListWithPaging query");
		page = replyService.getListWithPaging(board.getBno(), pageable);
		log.info("getListWithPaging query done");
		pageMaker = new PageMaker<R>(page);
		pageTest(pageMaker,true,1,10,21,-1,11);
		
		pageable = new PageVO(3).makePageble(0, "path");
		//세번째 페이지
		log.info("getListWithPaging query");
		page = replyService.getListWithPaging(board.getBno(), pageable);
		log.info("getListWithPaging query done");
		pageMaker = new PageMaker<R>(page);
		pageTest(pageMaker,true,3,10,21,-1,11);
		
		pageable = new PageVO(10).makePageble(0, "path");
		//열번째 페이지
		log.info("getListWithPaging query");
		page = replyService.getListWithPaging(board.getBno(), pageable);
		log.info("getListWithPaging query done");
		pageMaker = new PageMaker<R>(page);
		pageTest(pageMaker,true,10,10,21,-1,11);
		
		pageable = new PageVO(11).makePageble(0, "path");
		//11번째 페이지
		log.info("getListWithPaging query");
		page = replyService.getListWithPaging(board.getBno(), pageable);
		log.info("getListWithPaging query done");
		pageMaker = new PageMaker<R>(page);
		pageTest(pageMaker,true,11,10,21,10,21);
		
		pageable = new PageVO(20).makePageble(0, "path");
		//20번째 페이지
		log.info("getListWithPaging query");
		page = replyService.getListWithPaging(board.getBno(), pageable);
		log.info("getListWithPaging query done");
		pageMaker = new PageMaker<R>(page);
		pageTest(pageMaker,true,20,10,21,10,21);
		
		pageable = new PageVO(21).makePageble(0, "path");
		//21번째 페이지
		log.info("getListWithPaging query");
		page = replyService.getListWithPaging(board.getBno(), pageable);
		log.info("getListWithPaging query done");
		pageMaker = new PageMaker<R>(page);
		pageTest(pageMaker,true,21,1,21,20,-1);
		
		pageable = new PageVO(30).makePageble(0, "path");
		//30번째 페이지
		log.info("getListWithPaging query");
		page = replyService.getListWithPaging(board.getBno(), pageable);
		log.info("getListWithPaging query done");
		pageMaker = new PageMaker<R>(page);
		pageTest(pageMaker,false,30,1,21,20,-1);
	}
	@Test
	public void deleteTest() {
		log.info("deleteTest start");
		boardRepo.deleteAll();
		replyRepo.deleteAll();
		B board = makeOneBoard("board","board");
		boardService.addBoard(board);
		R reply = makeOneReply(board,-1L,"reply","reply");
		replyService.addReply(reply);
		assertEquals(replyRepo.count(),1);
		log.info("deleteReply query");
		replyService.deleteReply(reply.getRno());
		log.info("deleteReply query done");
		assertEquals(replyRepo.count(),1);
		R deleteReply = replyRepo.findById(reply.getRno()).get();
		assertEquals(deleteReply.getReply(),"삭제된 내용");
	}
	@Test
	public void hierarchicaltest() {
		boardRepo.deleteAll();
		replyRepo.deleteAll();
		B board = makeOneBoard("board","board");
		boardService.addBoard(board);
		
		R reply1 = makeOneReply(board,-1L,"user1","reply1");
		replyService.addReply(reply1);
		R reply1_1 = makeOneReply(board,reply1.getRno(),"user1","reply1_1");
		replyService.addReply(reply1_1);
		R reply1_2 = makeOneReply(board,reply1.getRno(),"user1","reply1_2");
		replyService.addReply(reply1_2);
		R reply1_3 = makeOneReply(board,reply1.getRno(),"user1","reply1_3");
		replyService.addReply(reply1_3);
		
		R reply2 = makeOneReply(board,-1L,"user1","reply2");
		replyService.addReply(reply2);
		R reply2_1 = makeOneReply(board,reply2.getRno(),"user1","reply2_1");
		replyService.addReply(reply2_1);
		R reply1_4 = makeOneReply(board,reply1.getRno(),"user1","reply1_4");
		replyService.addReply(reply1_4);
		R reply2_2 = makeOneReply(board,reply2.getRno(),"user1","reply2_2");
		replyService.addReply(reply2_2);
		
		R reply3 = makeOneReply(board,-1L,"user1","reply3");
		replyService.addReply(reply3);
		R reply3_1 = makeOneReply(board,reply3.getRno(),"user1","reply3_1");
		replyService.addReply(reply3_1);
		R reply2_3 = makeOneReply(board,reply2.getRno(),"user1","reply2_3");
		replyService.addReply(reply2_3);
		R reply3_2 = makeOneReply(board,reply3.getRno(),"user1","reply3_2");
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
		Page<R> page = replyService.getListWithPaging(board.getBno(), pageable);
		PageMaker<R> pageMaker = new PageMaker<R>(page);
		
		
		List<R> replies = pageMaker.getResult().getContent();
		assertEquals(replies.size(),10);
		assertEquals(replies.get(0).getReply(),"reply1");
		assertEquals(replies.get(1).getReply(),"reply1_1");
		assertEquals(replies.get(2).getReply(),"reply1_2");
		assertEquals(replies.get(3).getReply(),"reply1_3");
		assertEquals(replies.get(4).getReply(),"reply1_4");
		assertEquals(replies.get(5).getReply(),"reply2");
		assertEquals(replies.get(6).getReply(),"reply2_1");
		assertEquals(replies.get(7).getReply(),"reply2_2");
		assertEquals(replies.get(8).getReply(),"reply2_3");
		assertEquals(replies.get(9).getReply(),"reply3");
		
		pageable = new PageVO(2).makePageble(1,"path");
		//두번째 페이지 조사
		page = replyService.getListWithPaging(board.getBno(), pageable);
		pageMaker = new PageMaker<R>(page);
		replies = pageMaker.getResult().getContent();
		assertEquals(replies.size(),2);
		assertEquals(replies.get(0).getReply(),"reply3_1");
		assertEquals(replies.get(1).getReply(),"reply3_2");
	}
	private void pageTest(
				PageMaker<R> pageMaker,
				boolean isaccessible,
				int currentPageNum,
				int currentPageListSize,
				int totalPageNum,
				int prevPageNum,
				int nextPageNum
			) {
		assertEquals(pageMaker.getCurrentPageNum(),currentPageNum);
		assertEquals(pageMaker.isAccessible(),isaccessible);
		assertEquals(pageMaker.getPageList().size(),currentPageListSize);
		assertEquals(pageMaker.getTotalPageNum(),totalPageNum);
		if(prevPageNum == -1)
			assertNull(pageMaker.getPrevPage());
		else
			assertEquals(pageMaker.getPrevPage().getPageNumber()+1,prevPageNum);
		if(nextPageNum == -1)
			assertNull(pageMaker.getNextPage());
		else
			assertEquals(pageMaker.getNextPage().getPageNumber()+1,nextPageNum);
	}
	private void equalCount(B board,long count) {
		assertEquals(replyRepo.count(replyRepo.makePredicate(board.getBno())),count);
	}
}
