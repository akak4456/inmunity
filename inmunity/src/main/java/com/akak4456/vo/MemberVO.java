package com.akak4456.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.akak4456.constant.RegexpConstant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberVO {
	@NotNull(message="idnotnull")
	@NotEmpty(message="idnotnull")
	@Pattern(regexp=RegexpConstant.ID_REGEXP,
	message="idregexp")
	private String userid;
	
	@NotNull(message="pwnotnull")
	@NotEmpty(message="pwnotnull")
	@Pattern(regexp=RegexpConstant.PW_REGEXP,
			message="pwregexp")
	private String userpw;
	
	@NotNull(message="emailnotnull")
	@NotEmpty(message="emailnotnull")
	@Pattern(regexp=RegexpConstant.EMAIL_REGEXP,message="emailregexp")
	private String useremail;
	
	@Override
	public String toString() {
		return "userid:"+userid+" userpw:"+userpw+" useremail:"+useremail;
	}
	
}
