package com.akak4456.persistent.reply;

import org.springframework.stereotype.Repository;

import com.akak4456.domain.reply.FreeReply;
import com.akak4456.domain.reply.QFreeReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
@Repository
public interface FreeReplyRepository extends ReplyRepository<FreeReply> {
	@Override
	public default Predicate makePredicate(Long bno) {
		BooleanBuilder builder = new BooleanBuilder();
		QFreeReply reply = QFreeReply.freeReply;
		
		builder.and(reply.rno.gt(0));
		builder.and(reply.board.bno.eq(bno));
		return builder;
	}
}
