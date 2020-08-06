package com.akak4456.persistent.reply;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import com.akak4456.domain.reply.Reply;
import com.querydsl.core.types.Predicate;
@NoRepositoryBean
public interface ReplyRepository <T extends Reply> extends CrudRepository<T, Long>, QuerydslPredicateExecutor<T> {
	public Predicate makePredicate(Long bno);
}
