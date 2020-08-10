package com.akak4456.persistent.board;

import org.springframework.stereotype.Repository;

import com.akak4456.domain.board.FreeBoard;
import com.akak4456.domain.board.QFreeBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import lombok.extern.java.Log;
@Repository
public interface FreeBoardRepository extends BoardRepository<FreeBoard> {
	@Override
	public default Predicate makePredicate(String type,String keyword) {
		BooleanBuilder builder = new BooleanBuilder();
		QFreeBoard board = QFreeBoard.freeBoard;
		
		builder.and(board.bno.gt(0));
		
		if(type == null) {
			return builder;
		}
		//type은 단수만 받기로 하자
		//System.out.println("TYPE:"+type);
		//System.out.println("KEYWORD:"+keyword);
		if(type.equals("T")) {
			builder.and(board.title.like("%"+keyword+"%"));
		}else if(type.equals("C")) {
			builder.and(board.content.like("%"+keyword+"%"));
		}else if(type.equals("W")) {
			builder.and(board.username.like("%"+keyword+"%"));
		}else if(type.equals("E")) {
			builder.and(board.useremail.like("%"+keyword+"%"));
		}
		return builder;
	}
}
