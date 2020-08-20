package com.akak4456.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.scrap.Scrap;
import com.akak4456.domain.scrap.ScrapId;
import com.akak4456.exception.ScrapAlreadyExist;
import com.akak4456.persistent.MemberRepository;
import com.akak4456.persistent.ScrapRepository;
import com.akak4456.persistent.board.BoardRepository;

@Service
public class ScrapServiceImpl implements ScrapService {
	@Autowired
	private BoardRepository<Board> boardRepo;
	@Autowired
	private MemberRepository memberRepo;
	@Autowired
	private ScrapRepository scrapRepo;
	@Override
	public void addScrap(String useremail, Long bno) throws ScrapAlreadyExist {
		// TODO Auto-generated method stub
		ScrapId id = new ScrapId(bno,useremail);
		if(scrapRepo.findById(id).isPresent())
			throw new ScrapAlreadyExist();
		Board board = boardRepo.findById(bno).get();
		MemberEntity member = memberRepo.findById(useremail).get();
		
		
		Scrap scrap = Scrap.builder().id(id).board(board).member(member).build();	
		
		scrapRepo.save(scrap);
	}

	@Override
	public Page<Scrap> getListWithUseremail(String useremail, Pageable pageable) {
		// TODO Auto-generated method stub
		return scrapRepo.findAllByUseremail(useremail, pageable);
	}

}
