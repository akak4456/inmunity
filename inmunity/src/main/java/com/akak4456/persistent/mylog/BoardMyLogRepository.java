package com.akak4456.persistent.mylog;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.akak4456.domain.board.Board;

@Repository
public class BoardMyLogRepository {
	@PersistenceContext
	EntityManager em;
	
	public List<Board> findAllByUseremail(String useremail){
		return em.createQuery("select b from Board b where b.useremail=:useremail",Board.class).setParameter("useremail",useremail).getResultList();
	}
}
