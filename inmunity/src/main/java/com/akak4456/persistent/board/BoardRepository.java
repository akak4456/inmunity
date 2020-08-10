package com.akak4456.persistent.board;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.akak4456.domain.board.Board;
import com.querydsl.core.types.Predicate;

@NoRepositoryBean
public interface BoardRepository<T extends Board> extends CrudRepository<T, Long>,QuerydslPredicateExecutor<T> {
	public Predicate makePredicate(String type,String keyword);
}