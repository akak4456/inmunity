package com.akak4456.persistent.reply;

import org.springframework.stereotype.Repository;

import com.akak4456.domain.reply.GraduateReply;
import com.akak4456.domain.reply.QGraduateReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

@Repository
public interface GraduateReplyRepository extends ReplyRepository<GraduateReply> {
	public default Predicate makePredicate(Long bno) {
		BooleanBuilder builder = new BooleanBuilder();
		QGraduateReply reply = QGraduateReply.graduateReply;
		builder.and(reply.rno.gt(0));
		builder.and(reply.board.bno.eq(bno));
		return builder;
	}
}
