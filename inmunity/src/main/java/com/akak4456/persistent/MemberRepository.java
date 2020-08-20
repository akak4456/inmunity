package com.akak4456.persistent;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.akak4456.domain.member.EmailCheck;
import com.akak4456.domain.member.MemberEntity;

public interface MemberRepository extends CrudRepository<MemberEntity, String> {
	@Modifying
	@Transactional
	@Query("update MemberEntity m set m.authKey = ?1 where m.userid=?2")
	void setAuthKeyById(String authKey,String id);
	
	@Modifying
	@Transactional
	@Query("update MemberEntity m set m.emailCheck = ?1 where m.userid=?2")
	void setEmailCheckById(EmailCheck check,String id);
	
	Optional<MemberEntity> findByUserid(String id);
	
	@Modifying
	@Transactional
	@Query("update MemberEntity m set m.picture = ?1 where m.useremail=?2")
	void setPictureByUseremail(String picture,String useremail);
}
