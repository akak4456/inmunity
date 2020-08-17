package com.akak4456.domain.member;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@ToString
@Entity
@EqualsAndHashCode(of="useremail")
public class MemberEntity {
	
	@Id
	private String useremail;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private Role role;
	//한사람에게 부여되는 역할의 수는 한개만
	
	private String userid;//기본 회원 인증용
	private String userpw;//기본 회원 인증용
	
	
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private EmailCheck emailCheck;
	
	private String authKey;
	
	@NotNull
	private String name;
	
	private String picture;

	public void setName(String username) {
		// TODO Auto-generated method stub
		this.name = username;
	}

	public void setUserpw(String userpw) {
		// TODO Auto-generated method stub
		this.userpw = userpw;
	}
	

}
