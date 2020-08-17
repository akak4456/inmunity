package com.akak4456.persistent;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.akak4456.domain.board.Board;

@Repository
public interface BoardRepository extends CrudRepository<Board, Long>{
	@Query("select b,b.member.name from Board b inner join b.member")
	public Page<Board> findAll(Pageable pageable);
	
	@Query("select b,b.member.name from Board b inner join b.member where b.title like %:keyword%")
	public Page<Board> findAllByTitleLike(@Param("keyword")String keyword,Pageable pageable);
	
	@Query("select b,b.member.name from Board b inner join b.member where b.content like %:keyword%")
	public Page<Board> findAllByContentLike(@Param("keyword")String keyword,Pageable pageable);
	
	@Query("select b,b.member.name from Board b inner join b.member where b.member.name like %:keyword%")
	public Page<Board> findAllByNameLike(@Param("keyword")String keyword,Pageable pageable);
	
	@Query("select b,b.member.name from Board b inner join b.member where b.member.useremail like %:keyword%")
	public Page<Board> findAllByEmailLike(@Param("keyword")String keyword,Pageable pageable);
	
	@Query("select b from Board b where b.member.useremail=:useremail")
	public Page<Board> findAllByUseremail(@Param("useremail")String useremail,Pageable pageable);
	
	@Modifying
	@Transactional
	@Query("update Board b set b.member=null where b.member.useremail=:useremail")
	public void changeToDeleteByUseremail(@Param("useremail")String useremail);
	
	/*
	 * public default Predicate makePredicate(String type,String keyword) {
	 * BooleanBuilder builder = new BooleanBuilder(); QBoard board = QBoard.board;
	 * 
	 * builder.and(board.bno.gt(0));
	 * 
	 * if(type == null) { return builder; } //type은 단수만 받기로 하자
	 * //System.out.println("TYPE:"+type); //System.out.println("KEYWORD:"+keyword);
	 * if(type.equals("T")) { builder.and(board.title.like("%"+keyword+"%")); }else
	 * if(type.equals("C")) { builder.and(board.content.like("%"+keyword+"%"));
	 * }else if(type.equals("W")) {
	 * builder.and(board.member.name.like("%"+keyword+"%")); }else
	 * if(type.equals("E")) {
	 * builder.and(board.member.useremail.like("%"+keyword+"%")); } return builder;
	 * }
	 */
}
