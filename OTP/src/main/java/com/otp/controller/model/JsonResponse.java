package com.otp.controller.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Json response model which contains an error and the data.
 * 
 * @author Max
 *
 * @param <T>
 */
public class JsonResponse<T> {

	private List<JsonError> errors;
	private T data;

	public JsonResponse() {
		super();
	}

	public JsonResponse(List<JsonError> errors, T data) {
		super();
		this.errors = errors;
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<JsonError> getErrors() {
		return errors;
	}

	public void setErrors(List<JsonError> errors) {
		this.errors = errors;
	}

	/**
	 * Adds the given Json error to the list of errors.
	 * 
	 * @param error
	 */
	public void addError(JsonError error) {
		if (this.errors == null) {
			this.errors = new ArrayList<>();
		}
		this.errors.add(error);
	}

}
