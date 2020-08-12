package com.akak4456.persistent.boardandreplygeneral;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akak4456.domain.reply.QReply;
import com.akak4456.domain.reply.Reply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

@Repository
public interface ReplyGeneralRepository extends CrudRepository<Reply, Long>,QuerydslPredicateExecutor<Reply> {
	@Modifying
	@Query("update Reply r set r.useremail='none' where r.useremail=:useremail")
	public void changeToDeleteByUseremail(@Param("useremail")String useremail);
	public default Predicate makePredicate(String useremail) {
		BooleanBuilder builder = new BooleanBuilder();
		QReply re = QReply.reply1;
		
		builder.and(re.rno.gt(0));
		builder.and(re.useremail.eq(useremail));
		return builder;
	}
}
