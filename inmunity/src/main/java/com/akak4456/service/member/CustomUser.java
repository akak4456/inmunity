package com.akak4456.service.member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.member.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUser extends User {
	
	private MemberEntity member;

	public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		// TODO Auto-generated constructor stub
	}
	
	public CustomUser(MemberEntity member) {
		super(member.getUserid(), member.getUserpw(), makeGrantedAuthority(member.getRole()));
		this.member = member;
	}
	
	private static List<GrantedAuthority> makeGrantedAuthority(Role roles){
		List<GrantedAuthority> list = new ArrayList<>();
		if(roles == Role.ROLE_ADMIN) {
			list.add(new SimpleGrantedAuthority(Role.ROLE_ADMIN.getValue()));
		}else if(roles == Role.ROLE_MEMBER) {
			list.add(new SimpleGrantedAuthority(Role.ROLE_MEMBER.getValue()));
		}
		return list;
	}

}
