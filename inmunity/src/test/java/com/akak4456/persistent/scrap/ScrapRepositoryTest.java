package com.akak4456.persistent.scrap;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
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

import lombok.extern.java.Log;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmunityApplication.class)
@Log
@Commit
public class ScrapRepositoryTest {
	@Autowired
	private BoardRepository boardRepo;
	@Autowired
	private MemberRepository memberRepo;
	@Autowired
	private ScrapRepository repo;
	@Test
	public void addTest() {
		repo.deleteAll();
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
		
		Scrap scrap = new Scrap();
		scrap.setBoard(board);
		scrap.setMember(member);
		
		repo.save(scrap);
	}
	@Test(expected=DataIntegrityViolationException.class)
	public void addTestFail() {
		repo.deleteAll();
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
		
		Scrap scrap = new Scrap();
		scrap.setBoard(board);
		scrap.setMember(member);
		
		repo.save(scrap);
		
		Scrap scrap2 = new Scrap();
		scrap2.setBoard(board);
		scrap2.setMember(member);
		
		repo.save(scrap2);//같은걸 두번 저장하면 오류가 나야함
	}
}
