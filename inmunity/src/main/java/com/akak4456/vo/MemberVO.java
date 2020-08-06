package com.akak4456.vo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberVO {
	@NotNull(message=RegexpCheckConstants.ID_NOTNULL_FAIL)
	@NotEmpty(message=RegexpCheckConstants.ID_NOTNULL_FAIL)
	@Pattern(regexp=RegexpCheckConstants.ID_REGEXP,
	message=RegexpCheckConstants.ID_REGEXP_FAIL)
	private String userid;
	
	@NotNull(message=RegexpCheckConstants.PW_NOTNULL_FAIL)
	@NotEmpty(message=RegexpCheckConstants.PW_NOTNULL_FAIL)
	@Pattern(regexp=RegexpCheckConstants.PW_REGEXP,
			message=RegexpCheckConstants.PW_REGEXP_FAIL)
	private String userpw;
	
	@NotNull(message=RegexpCheckConstants.EMAIL_NOTNULL_FAIL)
	@NotEmpty(message=RegexpCheckConstants.EMAIL_NOTNULL_FAIL)
	@Pattern(regexp=RegexpCheckConstants.EMAIL_REGEXP,message=RegexpCheckConstants.EMAIL_REGEXP_FAIL)
	private String useremail;
	
	@Override
	public String toString() {
		return "userid:"+userid+" userpw:"+userpw+" useremail:"+useremail;
	}
	
}
