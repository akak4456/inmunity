package com.akak4456.persistent.reply;

import org.springframework.stereotype.Repository;

import com.akak4456.domain.reply.NotifyReply;
import com.akak4456.domain.reply.QNotifyReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
@Repository
public interface NotifyReplyRepository extends ReplyRepository<NotifyReply> {
	public default Predicate makePredicate(Long bno) {
		BooleanBuilder builder = new BooleanBuilder();
		QNotifyReply reply = QNotifyReply.notifyReply;
		builder.and(reply.rno.gt(0));
		builder.and(reply.board.bno.eq(bno));
		return builder;
	}
}
