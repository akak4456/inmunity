package com.akak4456.service.scrap;

import static org.junit.Assert.assertEquals;

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
import com.akak4456.domain.board.FreeBoard;
import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.member.Role;
import com.akak4456.domain.scrap.Scrap;
import com.akak4456.inmunity.InmunityApplication;
import com.akak4456.persistent.board.BoardRepository;
import com.akak4456.persistent.member.MemberRepository;
import com.akak4456.persistent.scrap.ScrapRepository;
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
	private MemberRepository memberRepo;
	@Autowired
	private ScrapRepository scrapRepo;
	@Autowired
	private ScrapService scrapService;
	
	
	
	@Test
	public void addScrapTest() {
		scrapRepo.deleteAll();
		Board board = new FreeBoard();
		board.setTitle("title");
		board.setContent("content");
		board.setUseremail("akak4478@naver.com");
		board.setUsername("akak4456");
		boardRepo.save(board);
		if(memberRepo.findById("akak4478@naver.com").isPresent())
		memberRepo.deleteById("akak4478@naver.com");
		MemberEntity member = new MemberEntity();
		member.setUseremail("akak4478@naver.com");
		member.setName("akak4456");
		member.setRole(Role.ROLE_MEMBER);
		memberRepo.save(member);
		log.info("addScrap query");
		scrapService.addScrap(member.getUseremail(),board.getBno() );
		log.info("addScrap query done");
	}
	@Test
	public void listTest() {
		scrapRepo.deleteAll();
		if(memberRepo.findById("akak4478@naver.com").isPresent())
			memberRepo.deleteById("akak4478@naver.com");
		MemberEntity member = new MemberEntity();
		member.setUseremail("akak4478@naver.com");
		member.setName("akak4456");
		member.setRole(Role.ROLE_MEMBER);
		memberRepo.save(member);
		List<Board> boards = boardRepo.findAll(boardRepo.makePredicate(null, null),new PageVO().makePageble(0, "bno")).getContent();
		for(int i=0;i<Math.min(boards.size(), 100);i++) {
			log.info(boards.get(i).getTitle());
			Scrap scrap = new Scrap();
			scrap.setMember(member);
			scrap.setBoard(boards.get(i));
			scrapRepo.save(scrap);
			//100개의 scrap저장
		}
		PageVO pageVO = new PageVO();
		pageVO.setKeyword("akak4478@naver.com");
		Pageable pageable = pageVO.makePageble(0, "sno");
		log.info("getListWithUseremail query");
		Page<Scrap> page = scrapService.getListWithUseremail("akak4478@naver.com", pageable);
		log.info("getListWithUseremail query done");
		assertEquals(page.getSize(),10);
	}
}
