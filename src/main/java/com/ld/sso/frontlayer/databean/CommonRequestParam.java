package com.ld.sso.frontlayer.databean;

import java.io.Serializable;

public class CommonRequestParam implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private CommonRequestParamData params;
	
	private String sign;
	
	private String timestamp;

	public CommonRequestParamData getParams() {
		return params;
	}

	public void setParams(CommonRequestParamData params) {
		this.params = params;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
