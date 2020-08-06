package com.akak4456.persistent.member;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.akak4456.domain.member.EmailCheck;
import com.akak4456.domain.member.MemberEntity;
@Repository
public interface MemberRepository extends CrudRepository<MemberEntity,Long> {
	@Modifying
	@Query("update MemberEntity m set m.authKey = ?1 where m.userid=?2")
	void setAuthKeyById(String authKey,String id);
	
	@Modifying
	@Query("update MemberEntity m set m.emailCheck = ?1 where m.userid=?2")
	void setEmailCheckById(EmailCheck check,String id);
	
	Optional<MemberEntity> findByUseremail(String email);
	
	Optional<MemberEntity> findByUserid(String id);
}
