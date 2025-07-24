package com.ims.exception;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private String displayError(Model model, String date, String status, String message, String exception) {
		model.addAttribute("timestamp", date);
		model.addAttribute("status", status);
		model.addAttribute("message", message);
		model.addAttribute("exception", exception);
		return "error";
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public String handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			WebRequest req, Model model) {
		List<String> inputErrors = new ArrayList<>();
		ex.getAllErrors().forEach(err -> {
			inputErrors.add(err.getDefaultMessage());
		});
		return displayError(model, new Date().toString(), HttpStatus.BAD_REQUEST.toString(),
				inputErrors.toString(), req.getDescription(false));
	} 
	@ExceptionHandler(ProductException.class)
	public String handleException(Exception ex, WebRequest req, Model model) {

		ExceptionResponse error = new ExceptionResponse(new Date().toString(), HttpStatus.OK.toString(),
				ex.getLocalizedMessage(), req.getDescription(false));
		return displayError(model, new Date().toString(), HttpStatus.OK.toString(),
				ex.getLocalizedMessage(), req.getDescription(false));

	}


	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public String handleMethodArgumentTypeMismatchException(RuntimeException ex,
			WebRequest req, Model model) {
		ExceptionResponse error = new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.toString(),
				ex.getLocalizedMessage(), req.getDescription(false));
		return displayError(model, new Date().toString(), HttpStatus.BAD_REQUEST.toString(),
				ex.getLocalizedMessage(), req.getDescription(false));
	}

	@ExceptionHandler(AccessDeniedException.class)
	public String handleAccessDeniedException(AccessDeniedException ex, WebRequest req, Model model) {
		ExceptionResponse error = new ExceptionResponse(new Date().toString(), HttpStatus.FORBIDDEN.toString(),
				ex.getLocalizedMessage(), req.getDescription(false));
		return displayError(model, new Date().toString(), HttpStatus.FORBIDDEN.toString(),
				ex.getLocalizedMessage(), req.getDescription(false));
	}

	@ExceptionHandler(RuntimeException.class)
	public String handleRuntimeException(RuntimeException ex, WebRequest req, Model model) {
		ExceptionResponse error = new ExceptionResponse(new Date().toString(),
				HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getLocalizedMessage(), req.getDescription(false));
		return displayError(model, new Date().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(),
				ex.getLocalizedMessage(), req.getDescription(false));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public String handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			WebRequest req, Model model) {
		ExceptionResponse error = new ExceptionResponse(new Date().toString(), HttpStatus.OK.name(),
				ex.getLocalizedMessage(), req.getDescription(false));
		return displayError(model, new Date().toString(), HttpStatus.OK.toString(),
				ex.getLocalizedMessage(), req.getDescription(false));
	}

}
