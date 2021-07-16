package com.softib.accountmanager.exception;

import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.softib.accountmanager.services.AccountServiceImpl;

@ControllerAdvice
public class AccountManagerExceptionAdvice {
	private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(AccountServiceImpl.class);

	@ResponseBody
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String accountNotFoundHandler(EntityNotFoundException ex) {
		logger.error(ex);
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(ProcessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	String internalError(ProcessException ex) {
		logger.error(ex);
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(AccountOperationException.class)
	@ResponseStatus(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS)
	String accountOpException(AccountOperationException ex) {
		logger.error(ex);
		return ex.getMessage();
	}
}
