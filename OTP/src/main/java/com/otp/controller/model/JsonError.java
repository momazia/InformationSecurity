package com.otp.controller.model;

/**
 * Json error model which holds an error code and a message
 * 
 * @author Max
 *
 */
public class JsonError {

	private Integer code;
	private String message;

	public JsonError(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
