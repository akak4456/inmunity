package com.akak4456.service.scrap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.scrap.Scrap;
import com.akak4456.persistent.boardandreplygeneral.BoardGeneralRepository;
import com.akak4456.persistent.member.MemberRepository;
import com.akak4456.persistent.scrap.ScrapRepository;

@Service
public class ScrapService {
	@Autowired
	private BoardGeneralRepository boardRepo;
	@Autowired
	private MemberRepository memberRepo;
	@Autowired
	private ScrapRepository scrapRepo;
	
	@Transactional
	public void addScrap(String useremail,Long bno) throws DataIntegrityViolationException {
		Board board = boardRepo.findById(bno).get();
		MemberEntity member = memberRepo.findById(useremail).get();
		
		Scrap scrap = new Scrap();
		scrap.setBoard(board);
		scrap.setMember(member);
		
		scrapRepo.save(scrap);
	}
	
	@Transactional
	public Page<Scrap> getListWithUseremail(String useremail,Pageable pageable){
		return scrapRepo.findAll(scrapRepo.makePredicate(useremail), pageable);
	}
}
