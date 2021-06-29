package com.example.tradestorage.exception;

public class InvalidTradeException extends RuntimeException {

	private final String errorCode;

	public InvalidTradeException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
