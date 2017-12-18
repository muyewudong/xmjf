package com.shsxt.xm.api.exceptions;

import com.shsxt.xm.api.constant.P2pConstant;

public class ParamsExcetion extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String errorMsg= P2pConstant.FAILED_MSG;
	private Integer errorCode=P2pConstant.FAILED_CODE;
	
	
	
	
	
	public ParamsExcetion(String errorMsg) {
		super(errorMsg);
		this.errorMsg = errorMsg;
	}
	public ParamsExcetion(String errorMsg, Integer errorCode) {
		super(errorMsg);
		this.errorMsg = errorMsg;
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	
	

}
