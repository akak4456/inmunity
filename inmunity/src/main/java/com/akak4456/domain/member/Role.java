package com.akak4456.domain.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum Role {
	ROLE_ADMIN(Constants.ADMIN_VALUE),
	ROLE_MEMBER(Constants.MEMBER_VALUE);
	
	public static class Constants{
		public static final String ADMIN_VALUE = "ROLE_ADMIN";
		public static final String MEMBER_VALUE = "ROLE_MEMBER";
	}
	
	private String value;
}
