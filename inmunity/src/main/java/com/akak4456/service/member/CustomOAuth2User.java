package com.akak4456.service.member;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import com.akak4456.domain.member.MemberEntity;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CustomOAuth2User extends DefaultOAuth2User {
	
	private MemberEntity member;
	
	public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes,
			String nameAttributeKey) {
		super(authorities, attributes, nameAttributeKey);
		// TODO Auto-generated constructor stub
	}
	
	public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes,
			String nameAttributeKey,MemberEntity member) {
		super(authorities, attributes, nameAttributeKey);
		// TODO Auto-generated constructor stub
		this.member = member;
	}

}
