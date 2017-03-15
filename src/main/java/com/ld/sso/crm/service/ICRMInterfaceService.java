package com.ld.sso.crm.service;

public interface ICRMInterfaceService {

	
	String getValidUserToken(String cmmemId);
	
	String generateNewAccessToken();
}
