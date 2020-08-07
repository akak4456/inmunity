package com.akak4456.constant;

public class RegexpConstant {
	public static final String ID_REGEXP = "^[A-Za-z0-9+]{5,50}$";
	public static final String PW_REGEXP = "^(?=.*[0-9]{1,50})(?=.*[~`!@#$%\\^&*()-+=]{1,50})(?=.*[a-zA-Z]{1,50}).{8,50}$";
	public static final String EMAIL_REGEXP = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
	private RegexpConstant() {
		
	}
}
