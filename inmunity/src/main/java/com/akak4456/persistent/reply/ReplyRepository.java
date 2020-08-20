package com.akak4456.persistent.reply;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface ReplyRepository<T extends Reply> extends CrudRepository<T, Long>, QuerydslPredicateExecutor<T> {
	@Modifying
	@Transactional
	@Query("delete from Reply r where r.board.bno=:bno")
	public void deleteByBno(@Param("bno") Long bno);

	@Modifying
	@Transactional
	@Query("update Reply r set r.member=null where r.member.useremail=:useremail")
	public void changeToDeleteByUseremail(@Param("useremail") String useremail);

	@Query("select r from Reply r where r.member.useremail=:useremail")
	public Page<Reply> findAllByUseremail(@Param("useremail") String useremail, Pageable pageable);
	public default Predicate makePredicate(Long bno) {
		BooleanBuilder builder = new BooleanBuilder();
		QReply reply = QReply.reply1;
		builder.and(reply.rno.gt(0));
		builder.and(reply.board.bno.eq(bno));
		return builder;
	}
}
