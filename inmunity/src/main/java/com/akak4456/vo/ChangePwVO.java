package com.akak4456.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.akak4456.constant.RegexpConstant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePwVO {
	private String useremail;
	
	@NotNull(message="pwnotnull")
	@NotEmpty(message="pwnotnull")
	@Pattern(regexp=RegexpConstant.PW_REGEXP,
			message="pwregexp")
	private String pw;
	
	@NotNull(message="pwnotnull")
	@NotEmpty(message="pwnotnull")
	@Pattern(regexp=RegexpConstant.PW_REGEXP,
			message="pwregexp")
	private String newpw;
}
