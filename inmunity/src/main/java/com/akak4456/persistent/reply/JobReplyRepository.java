package com.akak4456.persistent.reply;

import org.springframework.stereotype.Repository;

import com.akak4456.domain.reply.JobReply;
import com.akak4456.domain.reply.QJobReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
@Repository
public interface JobReplyRepository extends ReplyRepository<JobReply> {
	public default Predicate makePredicate(Long bno) {
		BooleanBuilder builder = new BooleanBuilder();
		QJobReply reply = QJobReply.jobReply;
		builder.and(reply.rno.gt(0));
		builder.and(reply.board.bno.eq(bno));
		return builder;
	}
}
