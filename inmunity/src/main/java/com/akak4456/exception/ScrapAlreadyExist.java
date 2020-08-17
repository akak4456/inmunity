package com.akak4456.exception;

public class ScrapAlreadyExist extends RuntimeException {
	private String message;
	private String details;
	private String hint;
	private String nextActions;
	private String support;

	public ScrapAlreadyExist() {
	}

	public ScrapAlreadyExist(String message, String details, String hint, String nextActions, String support) {
		this.message = message;
		this.details = details;
		this.hint = hint;
		this.nextActions = nextActions;
		this.support = support;
	}
}
