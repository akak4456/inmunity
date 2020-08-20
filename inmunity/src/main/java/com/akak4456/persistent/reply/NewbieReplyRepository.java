package com.akak4456.persistent.reply;

import org.springframework.stereotype.Repository;

import com.akak4456.domain.reply.NewbieReply;
import com.akak4456.domain.reply.QNewbieReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

@Repository
public interface NewbieReplyRepository extends ReplyRepository<NewbieReply> {
	public default Predicate makePredicate(Long bno) {
		BooleanBuilder builder = new BooleanBuilder();
		QNewbieReply reply = QNewbieReply.newbieReply;
		builder.and(reply.rno.gt(0));
		builder.and(reply.board.bno.eq(bno));
		return builder;
	}
}
