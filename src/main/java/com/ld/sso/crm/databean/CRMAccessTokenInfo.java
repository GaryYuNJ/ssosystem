package com.ld.sso.crm.databean;

import java.io.Serializable;
import java.util.Date;

public class CRMAccessTokenInfo implements Serializable{
	

	private static final long serialVersionUID = 1L;

	String accessToken;
	Date createDate;
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
