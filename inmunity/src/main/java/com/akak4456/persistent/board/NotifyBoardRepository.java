package com.akak4456.persistent.board;

import org.springframework.stereotype.Repository;

import com.akak4456.domain.board.NotifyBoard;
import com.akak4456.domain.board.QNotifyBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
@Repository
public interface NotifyBoardRepository extends BoardRepository<NotifyBoard> {
	public default Predicate makePredicate(String type, String keyword) {
		BooleanBuilder builder = new BooleanBuilder();
		QNotifyBoard board = QNotifyBoard.notifyBoard;

		builder.and(board.bno.gt(0));

		if (type == null) {
			return builder;
		} // type은 단수만 받기로 하자
		// System.out.println("TYPE:"+type); //System.out.println("KEYWORD:"+keyword);
		if (type.equals("T")) {
			builder.and(board.title.like("%" + keyword + "%"));
		} else if (type.equals("C")) {
			builder.and(board.content.like("%" + keyword + "%"));
		} else if (type.equals("W")) {
			builder.and(board.member.name.like("%" + keyword + "%"));
		} else if (type.equals("E")) {
			builder.and(board.member.useremail.like("%" + keyword + "%"));
		}
		return builder;
	}
}
