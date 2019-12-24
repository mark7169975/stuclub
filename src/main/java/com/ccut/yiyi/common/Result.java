package com.ccut.yiyi.common;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Result {

	
	private boolean success;
	private Integer code;
	private String message;
	private Object data;
	
	
	
	public Result(boolean success, Integer code, String message, Object data) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public Result(boolean success, Integer code, String message) {
		super();
		this.success = success;
		this.code = code;
		this.message = message;
	}
	

	
	
}
