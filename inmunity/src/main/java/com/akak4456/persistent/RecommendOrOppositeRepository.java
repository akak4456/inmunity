package com.akak4456.persistent;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akak4456.domain.recommendoropposite.RecommendOrOpposite;
import com.akak4456.domain.recommendoropposite.RecommendOrOppositeId;
@Repository
public interface RecommendOrOppositeRepository extends CrudRepository<RecommendOrOpposite, RecommendOrOppositeId> {
	@Modifying
	@Transactional
	@Query("delete from RecommendOrOpposite r where r.board.bno = :bno")
	public void deleteByBno(@Param("bno") Long bno);
	
	@Modifying
	@Transactional
	@Query("delete from RecommendOrOpposite r where r.member.useremail = :useremail")
	public void deleteByUseremail(@Param("useremail") String useremail);
}
