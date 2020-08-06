package com.akak4456.service.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.recommendoropposite.RecommendOrOppositeEntity;
import com.akak4456.inmunity.InmunityApplication;
import com.akak4456.persistent.board.BoardRepository;
import com.akak4456.persistent.recommendoropposite.RecommendOrOppositeRepository;
import com.akak4456.vo.PageMaker;
import com.akak4456.vo.PageVO;

import lombok.extern.java.Log;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmunityApplication.class)
@Log
@Commit
public abstract class BoardServiceTest<T extends Board, R extends RecommendOrOppositeEntity>{
	@Autowired
	protected BoardRepository<T> boardRepo;
	@Autowired
	protected BoardService<T,R> boardService;
	@Autowired
	protected RecommendOrOppositeRepository<R> repo2;
	
	public abstract void makeData(int count);
	
	public abstract R makeOneRecommendOrOppositeEntity(Long bno, String email,boolean isRecommend);
	
	public abstract T makeOne(String title,String content);
	@Test
	public void addBoardTest() {
		log.info("addBoardTest start");
		makeData(200);
		long cnt = boardRepo.count();
		assertEquals(cnt,200L);
		T board = makeOne("새로운 내용","새로운 제목");
		log.info("addBoard query");
		boardService.addBoard(board);
		log.info("addBoard query done");
		cnt = boardRepo.count();
		assertEquals(cnt,201L);
		T board2 = makeOne("새로운 내용2","새로운 제목2");
		log.info("addBoard query");
		boardService.addBoard(board2);
		log.info("addBoard query done");
		cnt = boardRepo.count();
		assertEquals(cnt,202L);
	}
	@Test
	public void getListWithPagingTest() {
		log.info("getListWithPagingTest start");
		makeData(205);
		
		Pageable pageable = new PageVO(0).makePageble(0,"bno");
		//pageVO에 1보다 작은 값을 넣는 것은 예외상황. 정상 동작하는지 확인. 첫번째 페이지
		log.info("getListWithPaging query");
		Page<T> page = boardService.getListWithPaging(null, null, pageable);
		log.info("getListWithPaging query done");
		PageMaker<T> pageMaker = new PageMaker<T>(page);
		pageTest(pageMaker,true,1,10,21,-1,11);
		
		pageable = new PageVO(2).makePageble(0,"bno");
		//2번째 페이지
		log.info("getListWithPaging query");
		page = boardService.getListWithPaging(null, null, pageable);
		log.info("getListWithPaging query done");
		pageMaker = new PageMaker<T>(page);
		pageTest(pageMaker,true,2,10,21,-1,11);
		
		pageable = new PageVO(10).makePageble(0,"bno");
		//10번째 페이지
		log.info("getListWithPaging query");
		page = boardService.getListWithPaging(null, null, pageable);
		log.info("getListWithPaging query done");
		pageMaker = new PageMaker<T>(page);
		pageTest(pageMaker,true,10,10,21,-1,11);
		
		pageable = new PageVO(11).makePageble(0,"bno");
		//11번째 페이지
		log.info("getListWithPaging query");
		page = boardService.getListWithPaging(null, null, pageable);
		log.info("getListWithPaging query done");
		pageMaker = new PageMaker<T>(page);
		pageTest(pageMaker,true,11,10,21,10,21);
		
		pageable = new PageVO(20).makePageble(0,"bno");
		//20번째 페이지
		log.info("getListWithPaging query");
		page = boardService.getListWithPaging(null, null, pageable);
		log.info("getListWithPaging query done");
		pageMaker = new PageMaker<T>(page);
		pageTest(pageMaker,true,20,10,21,10,21);
		
		pageable = new PageVO(21).makePageble(0,"bno");
		//21번째 페이지
		log.info("getListWithPaging query");
		page = boardService.getListWithPaging(null, null, pageable);
		log.info("getListWithPaging query done");
		pageMaker = new PageMaker<T>(page);
		pageTest(pageMaker,true,21,1,21,20,-1);
		
		pageable = new PageVO(30).makePageble(0,"bno");
		//30번째 페이지
		log.info("getListWithPaging query");
		page = boardService.getListWithPaging(null, null, pageable);
		log.info("getListWithPaging query done");
		pageMaker = new PageMaker<T>(page);
		pageTest(pageMaker,false,30,1,21,20,-1);
	}
	
	private void pageTest(
				PageMaker<T> pageMaker,
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
		log.info("getOneTest start");
		T board = makeOne("getOneTest 제목","getOneTest 내용");
		boardRepo.save(board);
		log.info("getOne query");
		T getOneBoard = boardService.getOne(board.getBno());
		log.info("getOne query done");
		equalBoard(getOneBoard,"getOneTest 제목","getOneTest 내용");
	}
	private void equalBoard(T board,String title,String content) {
		assertEquals(board.getTitle(),title);
		assertEquals(board.getContent(),content);
	}
	@Test(expected=NoSuchElementException.class)
	public void getOneFail() {
		T getBoard = boardService.getOne(999999999L);
		//존재할 수 없는 것에 대해 확인 예외가 없어야 한다
	}
	@Test
	public void modifyTest() {
		log.info("modifyTest start");
		T board = makeOne("수정할 제목","수정할 내용");
		boardRepo.save(board);
		T boardToUpdate = boardRepo.findById(board.getBno()).get();
		equalBoard(boardToUpdate,"수정할 제목","수정할 내용");
		boardToUpdate.setTitle("수정한 제목");
		boardToUpdate.setContent("수정한 내용");
		boardToUpdate.setFileUpload(null);
		assertNull(boardToUpdate.getFileUpload());
		log.info("modify query");
		boardService.modify(boardToUpdate.getBno(), boardToUpdate);
		log.info("modify query done");
		T boardToUpdate2 = boardRepo.findById(boardToUpdate.getBno()).get();
		equalBoard(boardToUpdate2,"수정한 제목","수정한 내용");
	}
	@Test(expected=NoSuchElementException.class)
	public void modifyFail(){
		T board = makeOne("title","content");
		boardService.modify(9999999999L, board);
	}
	@Test
	public void deleteTest() {
		log.info("deleteTest start");
		repo2.deleteAll();
		long cnt = boardRepo.count();
		T board = makeOne("삭제할 제목","삭제할 내용");
		boardService.addBoard(board);
		assertEquals(boardRepo.count(),cnt+1);
		
		R entity = makeOneRecommendOrOppositeEntity(board.getBno(),"akak4456@naver.com",true);
		boardService.upRecommendcnt(entity);
		R entity2 = makeOneRecommendOrOppositeEntity(board.getBno(),"akak4478@naver.com",true);
		boardService.upRecommendcnt(entity2);
		assertEquals(repo2.count(),2L);
		log.info("delete query");
		boardService.delete(board.getBno());
		log.info("delete query done");
		assertEquals(boardRepo.count(),cnt);
		assertEquals(repo2.count(),0L);
	}
	@Test(expected=EmptyResultDataAccessException.class)
	public void deleteFail() {
		boardService.delete(9999999999L);
	}
	@Test(expected=RecommendOrOppositeAlreadyExist.class)
	public void upRecommendcntTest() {
		boardRepo.deleteAll();
		repo2.deleteAll();
		log.info("upRecommendcntTest start");
		T board = makeOne("recommend test","recommend test");
		boardService.addBoard(board);
		
		Long bno = board.getBno();
		T getBoard = boardService.getOne(bno);
		long cnt = getBoard.getRecommendcnt();
		assertEquals(cnt,0L);
		
		R entity = makeOneRecommendOrOppositeEntity(bno,"akak4456@naver.com",true);
		log.info("upRecommendcnt query");
		boardService.upRecommendcnt(entity);
		log.info("upRecommendcnt query done");
		
		getBoard = boardService.getOne(bno);
		cnt = getBoard.getRecommendcnt();
		assertEquals(cnt,1L);
		
		boardService.upRecommendcnt(entity);
		
	}
	
	@Test(expected=RecommendOrOppositeAlreadyExist.class)
	public void upOppositecntTest() {
		boardRepo.deleteAll();
		repo2.deleteAll();
		log.info("upOppositecntTest start");
		
		T board = makeOne("opposite test","opposite test");
		boardService.addBoard(board);
		
		Long bno = board.getBno();
		
		T getBoard = boardService.getOne(bno);
		long cnt = getBoard.getOppositecnt();
		assertEquals(cnt,0L);
		
		R entity = makeOneRecommendOrOppositeEntity(bno,"akak4456@naver.com",false);
		log.info("upOppositecnt query");
		boardService.upOppositecnt(entity);
		log.info("upOppositecnt query done");
		
		getBoard = boardService.getOne(bno);
		cnt = getBoard.getOppositecnt();
		assertEquals(cnt,1L);
		
		boardService.upOppositecnt(entity);
		
	}
}
