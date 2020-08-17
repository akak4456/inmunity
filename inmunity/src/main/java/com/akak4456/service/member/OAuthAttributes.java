package com.akak4456.service.member;

import java.util.Map;

import com.akak4456.domain.member.EmailCheck;
import com.akak4456.domain.member.MemberEntity;
import com.akak4456.domain.member.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
	private Map<String,Object> attributes;
	private String nameAttributeKey;
	private String name;
	private String email;
	private String picture;
	
	@Builder
	public OAuthAttributes(Map<String,Object> attributes, 
			String nameAttributeKey, String name, String email,String picture) {
		this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
	}
	public static OAuthAttributes of(String registrationId,String userNameAttributeName,Map<String,Object> attributes) {
		return ofGoogle(userNameAttributeName,attributes);
	}
	
	public static OAuthAttributes ofGoogle(String userNameAttributeName,Map<String,Object> attributes) {
		return OAuthAttributes.builder()
				.email((String) attributes.get("email"))
				.name((String) attributes.get("name"))
				.picture((String) attributes.get("picture"))
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.build();
	}
	
	public MemberEntity toEntity() {
		return MemberEntity.builder()
				.name(this.name)
				.useremail(this.email)
				.emailCheck(EmailCheck.Y)
				.role(Role.ROLE_MEMBER)
				.picture(this.picture)
				.build();
	}
}
