package com.ahannon.ashlib.json.exceptions;

public class JSONFormattingException extends Exception {
	public JSONFormattingException(String errorMessage) {
		super(errorMessage);
	}

	public JSONFormattingException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}
}
