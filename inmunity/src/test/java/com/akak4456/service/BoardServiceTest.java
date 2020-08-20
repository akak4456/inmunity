package com.akak4456.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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
import com.akak4456.domain.fileupload.BoardFileUpload;
import com.akak4456.domain.member.EmailCheck;
import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.member.Role;
import com.akak4456.domain.recommendoropposite.RecommendOrOpposite;
import com.akak4456.domain.recommendoropposite.RecommendOrOppositeEnum;
import com.akak4456.domain.recommendoropposite.RecommendOrOppositeId;
import com.akak4456.domain.reply.Reply;
import com.akak4456.domain.scrap.Scrap;
import com.akak4456.domain.scrap.ScrapId;
import com.akak4456.exception.RecommendOrOppositeAlreadyExist;
import com.akak4456.inmunity.InmunityApplication;
import com.akak4456.persistent.FileUploadRepository;
import com.akak4456.persistent.MemberRepository;
import com.akak4456.persistent.RecommendOrOppositeRepository;
import com.akak4456.persistent.ScrapRepository;
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
public abstract class BoardServiceTest<B extends Board, R extends Reply> {
	@Autowired
	protected BoardRepository<B> boardRepo;
	@Autowired
	private FileUploadRepository fileUploadRepo;
	@Autowired
	private MemberRepository memberRepo; 
	@Autowired
	private BoardService<B> boardService;
	
	@Autowired
	private ReplyRepository<R> replyRepo;
	
	@Autowired
	private RecommendOrOppositeRepository roRepo;
	
	@Autowired
	private ScrapRepository scrapRepo;
	
	private MemberEntity member;
	
	private MemberEntity member2;
	protected abstract void makeBoard(MemberEntity member);
	protected abstract B makeOneBoard(String title,String content,MemberEntity member,List<BoardFileUpload> boardFileUpload);
	protected abstract R makeOneReply(String reply,MemberEntity member,B board);
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
		makeBoard(member);
		/*
		 * for(int i=0;i<=205;i++) { FreeBoard board = FreeBoard.builder()
		 * .title("title" + i) .content("content" + i) .member(member) .build();
		 * 
		 * boardRepo.save(board); }
		 */
	}
	@Test
	public void initTest() {
		
	}
	@Test
	public void addTest() {
		long cnt = boardRepo.count();
		List<BoardFileUpload> boardFileUpload = new ArrayList<>();
		boardFileUpload.add(BoardFileUpload.builder().uploadPath("2020/01/01").uploadFileName("name").build());
		boardFileUpload.add(BoardFileUpload.builder().uploadPath("2020/01/01").uploadFileName("name2").build());
		B board = makeOneBoard("title","content",member,boardFileUpload);
		log.info("addBoard query start");
		boardService.addBoard(board);
		log.info("addBoard query done");
		assertEquals(boardRepo.count(),cnt+1);
		assertEquals(fileUploadRepo.count(),boardFileUpload.size());
	}
	@Test
	public void getListWithPagingTest() {
		Pageable pageable = new PageVO(0).makePageble(0,"bno");
		//pageVO에 1보다 작은 값을 넣는 것은 예외상황. 정상 동작하는지 확인. 첫번째 페이지
		log.info("getListWithPaging query start");
		Page<B> page = boardService.getListWithPaging(null, null, pageable);
		log.info("getListWithPaging query done");
		PageMaker<B> pageMaker = new PageMaker<B>(page);
		pageTest(pageMaker,true,1,10,21,-1,11);
		
		pageable = new PageVO(2).makePageble(0,"bno");
		//2번째 페이지
		log.info("getListWithPaging query start");
		page = boardService.getListWithPaging(null, null, pageable);
		log.info("getListWithPaging query done");
		pageMaker = new PageMaker<B>(page);
		pageTest(pageMaker,true,2,10,21,-1,11);
		
		pageable = new PageVO(10).makePageble(0,"bno");
		//10번째 페이지
		log.info("getListWithPaging query start");
		page = boardService.getListWithPaging(null, null, pageable);
		log.info("getListWithPaging query done");
		pageMaker = new PageMaker<B>(page);
		pageTest(pageMaker,true,10,10,21,-1,11);
		
		pageable = new PageVO(11).makePageble(0,"bno");
		//11번째 페이지
		log.info("getListWithPaging query start");
		page = boardService.getListWithPaging(null, null, pageable);
		log.info("getListWithPaging query done");
		pageMaker = new PageMaker<B>(page);
		pageTest(pageMaker,true,11,10,21,10,21);
		
		pageable = new PageVO(20).makePageble(0,"bno");
		//20번째 페이지
		log.info("getListWithPaging query start");
		page = boardService.getListWithPaging(null, null, pageable);
		log.info("getListWithPaging query done");
		pageMaker = new PageMaker<B>(page);
		pageTest(pageMaker,true,20,10,21,10,21);
		
		pageable = new PageVO(21).makePageble(0,"bno");
		//21번째 페이지
		log.info("getListWithPaging query start");
		page = boardService.getListWithPaging(null, null, pageable);
		log.info("getListWithPaging query done");
		pageMaker = new PageMaker<B>(page);
		pageTest(pageMaker,true,21,1,21,20,-1);
		
		pageable = new PageVO(30).makePageble(0,"bno");
		//30번째 페이지
		log.info("getListWithPaging query start");
		page = boardService.getListWithPaging(null, null, pageable);
		log.info("getListWithPaging query done");
		pageMaker = new PageMaker<B>(page);
		pageTest(pageMaker,false,30,1,21,20,-1);
	}
	private void pageTest(
			PageMaker<B> pageMaker,
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
	@Test
	public void getOneTest() {
		B board =makeOneBoard("title","content",member,null);
		boardService.addBoard(board);
		log.info("getOne query start");
		Board getBoard = boardService.getOne(board.getBno());
		log.info("getOne query done");
		assertEquals(getBoard.getTitle(),board.getTitle());
		assertEquals(getBoard.getContent(),board.getContent());
	}
	@Test
	public void modifyTest() {
		List<BoardFileUpload> boardFileUpload = new ArrayList<>();
		boardFileUpload.add(BoardFileUpload.builder().uploadPath("2020/01/01").uploadFileName("name").build());
		boardFileUpload.add(BoardFileUpload.builder().uploadPath("2020/01/01").uploadFileName("name2").build());
		B board = makeOneBoard("title","content",member,boardFileUpload);
		boardService.addBoard(board);
		assertEquals(fileUploadRepo.count(),2L);
		List<BoardFileUpload> updateFileUpload = new ArrayList<>();
		updateFileUpload.add(BoardFileUpload.builder().uploadPath("2020/01/01").uploadFileName("name").build());
		updateFileUpload.add(BoardFileUpload.builder().uploadPath("2020/01/01").uploadFileName("name2").build());
		updateFileUpload.add(BoardFileUpload.builder().uploadPath("2020/01/01").uploadFileName("name3").build());
		FreeBoard update = FreeBoard.builder()
							.title("title")
							.content("content")
							.member(member)
							.fileUpload(updateFileUpload).build();
		log.info("modify query start");
		boardService.modify(board.getBno(), update);
		log.info("modify query end");
		assertEquals(fileUploadRepo.count(),3L);
		Board getBoard = boardService.getOne(board.getBno());
		assertEquals(getBoard.getTitle(),update.getTitle());
		assertEquals(getBoard.getContent(),update.getContent());
	}
	@Test
	public void deleteTest() {
		List<BoardFileUpload> boardFileUpload = new ArrayList<>();
		boardFileUpload.add(BoardFileUpload.builder().uploadPath("2020/01/01").uploadFileName("name").build());
		boardFileUpload.add(BoardFileUpload.builder().uploadPath("2020/01/01").uploadFileName("name2").build());
		B board = makeOneBoard("title","content",member,boardFileUpload);
		boardService.addBoard(board);
		R reply = makeOneReply("reply",member,board);
		replyRepo.save(reply);
		RecommendOrOppositeId id1 = new RecommendOrOppositeId(board.getBno(),member.getUseremail());
		RecommendOrOpposite ro = RecommendOrOpposite.builder().id(id1).board(board).member(member).recommendOrOpposite(RecommendOrOppositeEnum.Recommend).build();
		roRepo.save(ro);
		ScrapId id2 = new ScrapId(board.getBno(),member.getUseremail());
		Scrap scrap = Scrap.builder().id(id2).board(board).member(member).build();
		scrapRepo.save(scrap);
		assertEquals(replyRepo.count(),1L);
		assertEquals(roRepo.count(),1L);
		assertEquals(scrapRepo.count(),1L);
		log.info("delete query start");
		boardService.delete(board.getBno());
		log.info("delete query done");
		assertEquals(replyRepo.count(),0L);
		assertEquals(roRepo.count(),0L);
		assertEquals(scrapRepo.count(),0L);
		assertTrue(memberRepo.count()>0);
	}
	
	@Test(expected=RecommendOrOppositeAlreadyExist.class)
	public void recommendOrOppositeTest() {
		B board = makeOneBoard("title","content",member,null);
		boardService.addBoard(board);
		
		//추천 되는 지 확인
		RecommendOrOppositeId id1 = new RecommendOrOppositeId(board.getBno(),member.getUseremail());
		RecommendOrOpposite ro1 = RecommendOrOpposite.builder()
									.id(id1)
									.board(board)
									.member(member)
									.recommendOrOpposite(RecommendOrOppositeEnum.Recommend).build();
		log.info("upRecommendcnt query start");
		boardService.upRecommendcnt(ro1);
		log.info("upRecommendcnt query start");
		
		//반대 되는 지 확인 다른 멤버를 이용해야함
		RecommendOrOppositeId id2 = new RecommendOrOppositeId(board.getBno(),member2.getUseremail());
		RecommendOrOpposite ro2 = RecommendOrOpposite.builder()
									.id(id2)
									.board(board)
									.member(member2)
									.recommendOrOpposite(RecommendOrOppositeEnum.Opposite).build();
		
		log.info("upOppositecnt query start");
		boardService.upOppositecnt(ro2);
		log.info("upOppositecnt query done");
		boardService.upRecommendcnt(ro1);//exception 이 발생해야 함
		
	}
}
