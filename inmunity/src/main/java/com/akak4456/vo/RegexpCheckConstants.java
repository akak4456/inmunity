package com.akak4456.vo;

public class RegexpCheckConstants {
	public static final String ID_REGEXP = "^[A-Za-z0-9+]{5,50}$"; 
	public static final String ID_NOTNULL_FAIL = "아이디는 반드시 있어야 합니다";
	public static final String ID_REGEXP_FAIL = "아이디는 영어대소문자 숫자만 가능하며, 총 5글자 이상 50글자 이하 써야합니다";
	
	public static final String PW_REGEXP = "^(?=.*[0-9]{1,50})(?=.*[~`!@#$%\\^&*()-+=]{1,50})(?=.*[a-zA-Z]{1,50}).{8,50}$";
	public static final String PW_NOTNULL_FAIL="비밀번호는 반드시 있어야 합니다";
	public static final String PW_REGEXP_FAIL = "비밀번호는 영어대소문자, 숫자, 특수문자를 각각 1자 이상씩 써야하며, 총 8글자 이상 50글자 이하 써야합니다";
	
	public static final String EMAIL_REGEXP = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
	public static final String EMAIL_NOTNULL_FAIL = "이메일은 반드시 있어야 합니다";
	public static final String EMAIL_REGEXP_FAIL = "이메일 형식이 맞지 않습니다";
	
	public static final String NAME_NOTNULL_FAIL = "사용자 이름은 반드시 있어야 합니다";
	
	public static final String BOARD_TITLE_NOTNULL_FAIL = "제목은 있어야 합니다";
	public static final String BOARD_TITLE_MAXLEN_FAIL = "최대 100자까지만 입력가능합니다";
	public static final String BOARD_CONTENT_NOTNULL_FAIL = "내용은 있어야 합니다";
	private RegexpCheckConstants() {
		
	}
}
