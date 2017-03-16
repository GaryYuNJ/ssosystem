package com.ld.sso.frontlayer.databean;

import java.util.List;
import java.util.Map;

import com.ld.sso.midlayer.databean.CRMCustmemberBasicInfo;
import com.ld.sso.midlayer.databean.CRMCustmemberFullInfo;

public class CommonResponseInfo {

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
	
	//Map<String, Object> data;
	List <Map> data;
	String code;
	String msg;
		
	CRMCustmemberBasicInfo custmemberBasicInfo;
	CRMCustmemberFullInfo custmemberFullInfo;
	
	String ticket;
	
	public CRMCustmemberFullInfo getCustmemberFullInfo() {
		return custmemberFullInfo;
	}

	public void setCustmemberFullInfo(CRMCustmemberFullInfo custmemberFullInfo) {
		this.custmemberFullInfo = custmemberFullInfo;
	}

	public CRMCustmemberBasicInfo getCustmemberBasicInfo() {
		return custmemberBasicInfo;
	}

	public void setCustmemberBasicInfo(CRMCustmemberBasicInfo custmemberBasicInfo) {
		this.custmemberBasicInfo = custmemberBasicInfo;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}


	public List<Map> getData() {
		return data;
	}

	public void setData(List<Map> data) {
		this.data = data;
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

}
