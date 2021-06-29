package com.example.tradestorage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.tradestorage.exception.ClientErrorInformation;
import com.example.tradestorage.exception.InvalidTradeException;

@ControllerAdvice
public class TradeStorageControllerAdvice {
	
	/**
	 * Handling InvalidTradeException and return user understandable exception  
	 * @param e
	 * @return
	 */
	@ExceptionHandler(InvalidTradeException.class)
	public ResponseEntity<ClientErrorInformation> handleInvalidTradeException(final InvalidTradeException e) {
		return new ResponseEntity(new ClientErrorInformation(e.getErrorCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
	}
}
