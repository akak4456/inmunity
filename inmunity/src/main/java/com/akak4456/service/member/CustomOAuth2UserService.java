package com.akak4456.service.member;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.akak4456.domain.member.MemberEntity;
import com.akak4456.persistent.MemberRepository;
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	@Autowired
	private MemberRepository memberRepository;
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// TODO Auto-generated method stub
		OAuth2UserService delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);
		
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		//네이버인지 구글인지 구분
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
													.getUserInfoEndpoint().getUserNameAttributeName();
		
		OAuthAttributes attributes = OAuthAttributes
										.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
		
		MemberEntity entity = memberRepository.findById(attributes.getEmail())
												.orElseGet(()->memberRepository.save(attributes.toEntity()));
		
		
		return new	CustomOAuth2User(Collections.singleton(new SimpleGrantedAuthority(entity.getRole().getValue()))
	            , attributes.getAttributes()
	            , attributes.getNameAttributeKey(),entity);
	}
	
}
