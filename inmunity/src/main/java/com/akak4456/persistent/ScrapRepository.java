package com.akak4456.persistent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akak4456.domain.scrap.Scrap;
import com.akak4456.domain.scrap.ScrapId;

@Repository
public interface ScrapRepository extends CrudRepository<Scrap, ScrapId> {
	@Query("select s.board,type(s.board) from Scrap s inner join s.board where s.member.useremail=:useremail")
	public Page<Scrap> findAllByUseremail(@Param("useremail")String useremail, Pageable pageable);
	
	@Modifying
	@Query("delete from Scrap s where s.board.bno = :bno")
	public void deleteByBno(@Param("bno") Long bno);
	
	@Modifying
	@Query("delete from Scrap s where s.member.useremail = :useremail")
	public void deleteByUseremail(@Param("useremail") String useremail);
}