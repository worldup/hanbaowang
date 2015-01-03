package com.upbest.exception;

/**
 * 用户名密码不匹配异常
 * @author QunZheng
 *
 */
public class UsernameAndPasswordNotMatchException extends RuntimeException{

	public UsernameAndPasswordNotMatchException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UsernameAndPasswordNotMatchException(String message,
			Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public UsernameAndPasswordNotMatchException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UsernameAndPasswordNotMatchException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public UsernameAndPasswordNotMatchException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
}
