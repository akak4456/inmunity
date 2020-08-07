package com.akak4456.service.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.akak4456.domain.member.EmailCheck;
import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.member.Role;
import com.akak4456.persistent.member.MemberRepository;
import com.akak4456.vo.MemberVO;

@Service
public class MemberService implements UserDetailsService {

	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<MemberEntity> userEntityWrapper = memberRepository.findByUserid(username);
		MemberEntity userEntity = userEntityWrapper.get();
		
		return new CustomUser(userEntity);
	}
	
	public boolean isSameId(String userid) {
		//매개변수로 받은 userid랑 같은 아이디가 db상에 존재하는지 확인한다
		return memberRepository.findByUserid(userid).isPresent();
	}
	public boolean isSameEmail(String email) {
		return memberRepository.findByUseremail(email).isPresent();
	}
	@Transactional
	public void joinPost(MemberVO memberVO) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		MemberEntity member = MemberEntity.builder()
								.userid(memberVO.getUserid())
								.userpw(passwordEncoder.encode(memberVO.getUserpw()))
								.useremail(memberVO.getUseremail())
								.name(memberVO.getUserid())
								.role(Role.ROLE_MEMBER)
								.emailcheck(EmailCheck.N)
								.build();
		memberRepository.save(member);
	}
	
	@Transactional
	public void updateAuthKey(String authKey,String id) {
		memberRepository.setAuthKeyById(authKey, id);
	}
	
	@Transactional
	public boolean checkEmailCode(String authKey,String id) {
		return memberRepository.findByUserid(id).get().getAuthKey().equals(authKey);
	}
	
	@Transactional
	public void updateEmailCheck(EmailCheck emailCheck,String id) {
		memberRepository.setEmailCheckById(emailCheck, id);
	}
	@Transactional
	public boolean isEmailAuthenticated(String useremail) {
		return memberRepository.findByUseremail(useremail)
				.filter(m -> m.getEmailCheck() == EmailCheck.Y).isPresent();
	}

}
