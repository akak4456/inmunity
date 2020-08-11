package com.akak4456.service.mylog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.akak4456.domain.board.Board;
import com.akak4456.persistent.boardandreplygeneral.BoardGeneralRepository;

@Service
public class BoardMyLogService {
	@Autowired
	private BoardGeneralRepository repo;
	
	public Page<Board> getListWithUseremail(String useremail,Pageable pageable){
		return repo.findAll(repo.makePredicate("E", useremail),pageable);
		//return repo.findAllByUseremail(pageNumber,pageSize,useremail);
	}
}
