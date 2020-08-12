package com.akak4456.persistent.boardandreplygeneral;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akak4456.domain.board.Board;
import com.akak4456.domain.board.QBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
@Repository
public interface BoardGeneralRepository extends CrudRepository<Board, Long>,QuerydslPredicateExecutor<Board> {
	@Modifying
	@Query("update Board b set b.useremail='none' where b.useremail=:useremail")
	public void changeToDeleteByUseremail(@Param("useremail")String useremail);
	public default Predicate makePredicate(String type,String keyword) {
		BooleanBuilder builder = new BooleanBuilder();
		QBoard board = QBoard.board;
		
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
