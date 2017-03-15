package com.ld.sso.frontlayer.databean;

import java.io.Serializable;
import java.util.Map;

public class CommonRequestParam implements Serializable{

/**
 * 衔接CRM接口，通用 请求参数格式
 * 
 * 举例：
   {
	"params": {
		"ticket": "00000002383434", ---当前系统需要的
		"token": "00000002383434",  ---当前系统调用CRM需要加入的
		"p_token": "00000002383434", ---当前系统调用CRM需要加入的
		"num": 1
	},
	"sign": "ac9e29882aecdf941bc9221312544ef2d0b5aa21",
	"timestamp": "1489390123711"
	}

params:
	mobile;
	password;
	ticket;
	newPassword;
*/	
	
	private static final long serialVersionUID = 1L;

	
	private Map<String, Object> params;
	
	private String sign;
	
	private String timestamp;



	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
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
