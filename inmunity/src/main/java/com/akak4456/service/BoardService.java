package com.akak4456.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.recommendoropposite.RecommendOrOpposite;
import com.akak4456.exception.RecommendOrOppositeAlreadyExist;

public interface BoardService {
	public void addBoard(Board board);
	
	public Page<Board> getListWithPaging(String type,String keyword,Pageable pageable);
	
	public Board getOne(Long bno);
	
	public void modify(Long bno, Board board);
	
	public void delete (Long bno);
	
	public void upRecommendcnt(RecommendOrOpposite recommendOrOppositeEntity) throws RecommendOrOppositeAlreadyExist;
	
	public void upOppositecnt(RecommendOrOpposite recommendOrOppositeEntity) throws RecommendOrOppositeAlreadyExist;
	
	public Page<Board> getListWithUseremail(String useremail,Pageable pageable);
	
}
