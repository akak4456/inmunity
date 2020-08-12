package com.akak4456.persistent.scrap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akak4456.domain.scrap.QScrap;
import com.akak4456.domain.scrap.Scrap;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

@Repository
public interface ScrapRepository extends CrudRepository<Scrap, Long>,QuerydslPredicateExecutor<Scrap> {
	@Override
	@Query("select s.board from Scrap s left join s.board")
	public Page<Scrap> findAll(Predicate predicate, Pageable pageable);
	
	@Modifying
	@Query("delete from Scrap s where s.board.bno = :bno")
	public void deleteByBno(@Param("bno") Long bno);
	
	@Modifying
	@Query("delete from Scrap s where s.member.useremail = :useremail")
	public void deleteByUseremail(@Param("useremail") String useremail);
	
	public default Predicate makePredicate(String useremail) {
		BooleanBuilder builder = new BooleanBuilder();
		QScrap scrap = QScrap.scrap;
		builder.and(scrap.sno.gt(0));
		builder.and(scrap.member.useremail.eq(useremail));
		return builder;
	}
}
