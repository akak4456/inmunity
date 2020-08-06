package com.akak4456.persistent.recommendoropposite;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.akak4456.domain.recommendoropposite.RecommendOrOppositeEntity;
import com.akak4456.domain.recommendoropposite.RecommendOrOppositeId;

public interface RecommendOrOppositeRepository <T extends RecommendOrOppositeEntity> extends CrudRepository<T, RecommendOrOppositeId> {
	@Modifying
	@Query("delete from RecommendOrOppositeEntity r where r.id.bno = :bno")
	public void deleteByBno(@Param("bno") Long bno);
}
