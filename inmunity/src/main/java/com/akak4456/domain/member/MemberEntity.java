package com.akak4456.domain.member;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="tbl_member")
@EqualsAndHashCode(of="userid")
public class MemberEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private Role role;
	//한사람에게 부여되는 역할의 수는 한개만
	
	private String userid;//기본 회원 인증용
	private String userpw;//기본 회원 인증용
	
	@NotNull
	private String useremail;
	
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private EmailCheck emailCheck = EmailCheck.N;
	
	private String authKey;
	
	@NotNull
	private String name;
	
	private String picture = "none";
	
	public MemberEntity() {
		
	}
	
	
	@Builder
	public MemberEntity(String userid,String userpw,String useremail,Role role, EmailCheck emailcheck,String name,String picture) {
		this.userid = userid;
		this.userpw = userpw;
		this.useremail = useremail;
		this.role = role;
		this.emailCheck = emailcheck;
		this.name = name;
		this.picture = picture;
	}
}
