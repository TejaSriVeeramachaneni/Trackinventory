package com.ims.exception;

import lombok.*;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ExceptionResponse {

	public ExceptionResponse(String timestamp, String status, String error, String path) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.path = path;
	}

	String timestamp;
	String status;
	String error;
	String path;

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
 