package com.ld.sso.crm.service;

import java.math.BigDecimal;

import com.ld.sso.crm.databean.ResponseFromCRMData;
import com.ld.sso.crm.domain.CRMCustmemberModel;
import com.ld.sso.frontlayer.databean.CommonRequestParam;

public interface ICRMInterfaceService {

	
	String getValidUserTokenFromDB(String cmmemId);
	
	String generateNewAccessToken();

	CRMCustmemberModel authUserLogin(String mobile, String password);

	CRMCustmemberModel getFullInfoByPrimaryKey(String cmmemid);

	int modifyPassword(String cmmemid, String newPassword);

	int modifyUserInfoByPrimaryKey(CRMCustmemberModel cusModel);

	ResponseFromCRMData sendCommonRequestToCRM(String crmInterfaceCode, CommonRequestParam requestparam);

	BigDecimal getCurJFYEByCustId(String cmcustid);

	int modifyPasswordByMobile(String mobile, String newPassword);

	BigDecimal getCmczzByCustId(String cmcustid);
	
}
