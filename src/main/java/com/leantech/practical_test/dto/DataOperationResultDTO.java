package com.leantech.practical_test.dto;

import java.io.Serializable;

/**
 * DTO used to return a given operation result from the service back to the
 * controller
 * 
 * @author abaquero
 */
public class DataOperationResultDTO<T> implements Serializable {
	private static final long serialVersionUID = -4670101872812594528L;

	/** Operation attempted */
	private String operation;
	
	/** Result from the operation */
	private String result;
	
	/** Motive of failure */
	private String motive;
	
	/** Related object for the response as a generic */
	private T responseObject;

	/**
	 * @param operation
	 * @param result
	 * @param motive
	 * @param responseObject
	 */
	public DataOperationResultDTO(String operation, String result, String motive, T responseObject) {
		this.operation = operation;
		this.result = result;
		this.motive = motive;
		this.responseObject = responseObject;
	}

	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * @return the motive
	 */
	public String getMotive() {
		return motive;
	}

	/**
	 * @param motive the motive to set
	 */
	public void setMotive(String motive) {
		this.motive = motive;
	}

	/**
	 * @return the responseObject
	 */
	public T getResponseObject() {
		return responseObject;
	}

	/**
	 * @param responseObject the responseObject to set
	 */
	public void setResponseObject(T responseObject) {
		this.responseObject = responseObject;
	}
}
