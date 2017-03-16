package com.ld.sso.crm.databean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ResponseFromCRMData  implements Serializable{
	

	private static final long serialVersionUID = 1L;

	/*
{
    "appId": "test",
    "code": "0",
    "msg": "",
    "timestamp": "1489482814072",
    "data": [
        {
            "Cmreferee": null,
            "Crelation": "1",
            "Cmidno": null,
            "Cmidtype": null,
            "Cdmstatus": "Y",
            "Cdmmaxdate": "2099-12-31",
            "Cdmmindate": "2017-02-10",
            "Cmtotjf": 0,
            "Cmptname": null,
            "Cmcustid": "0000025615",
            "Cmmobile1": "15757116092",
            "Ctname": "银卡",
            "Cmemail": null,
            "Cmmemid": "0000025599",
            "Cmaddr": null,
            "Cdmtype": "01",
            "Cmczz": null,
            "Cmname": "会员6092",
            "Cmbirthday": null,
            "Cmsex": "F"
        }
    ],
    "sign": "f37ed430757c7fa01f300345ebb71a877666b59a"
}	
	
*/	
	List <Map> data;
	//Map<String, Object> data;
	String appId;
	String code;
	String msg;
	String timestamp;
	String sign;
	String access_token;
	String expires_in;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	
	public List<Map> getData() {
		return data;
	}
	public void setData(List<Map> data) {
		this.data = data;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
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
