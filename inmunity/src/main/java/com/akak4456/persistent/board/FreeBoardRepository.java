package com.akak4456.persistent.board;

import org.springframework.stereotype.Repository;

import com.akak4456.domain.board.FreeBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
@Repository
public interface FreeBoardRepository extends BoardRepository<FreeBoard> {
	@Override
	public default Predicate makePredicate(String type,String keyword) {
		BooleanBuilder builder = new BooleanBuilder();
		/*
		QFreeBoard board = QCommunityBoard.communityBoard;
		
		builder.and(board.bno.gt(0));
		
		if(type == null) {
			return builder;
		}
		Predicate[] predicates = new Predicate[type.length()];
		for(int i=0;i<type.length();i++) {
			if(type.charAt(i) == 'T') {
				predicates[i] = board.title.like("%"+keyword+"%");
			}
			else if(type.charAt(i) == 'C') {
				predicates[i] = board.content.like("%"+keyword+"%");
			}
			else if(type.charAt(i) == 'W') {
				predicates[i] = board.userid.like("%"+keyword+"%");
			}
		}
		builder.andAnyOf(predicates);
		*/
		return builder;
	}
}
