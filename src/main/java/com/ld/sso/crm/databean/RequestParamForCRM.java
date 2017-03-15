package com.ld.sso.crm.databean;

import java.io.Serializable;
import java.util.Map;

public class RequestParamForCRM  implements Serializable{
	

	private static final long serialVersionUID = 1L;

	/*
{
	"params": {
		"token": "00000002383434",
		"num": 1,
        "p_token": "00000002383434",
        'name':'123213',
        "num2": null,
        'dddd3':2
	},
	"sign": "ac9e29882aecdf941bc9221312544ef2d0b5aa21",
	"timestamp": "1489390123711"
}
	
*/	
	//Map params;
	RequestParamForCRMData params;
	String timestamp;
	String sign;
	
	public RequestParamForCRMData getParams() {
		return params;
	}
	public void setParams(RequestParamForCRMData params) {
		this.params = params;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
