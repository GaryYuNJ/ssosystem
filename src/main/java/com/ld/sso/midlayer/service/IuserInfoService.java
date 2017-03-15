package com.ld.sso.midlayer.service;

import com.ld.sso.midlayer.databean.CRMCustmemberBasicInfo;

public interface IuserInfoService {

	CRMCustmemberBasicInfo queryUserInfoByTicket(String ticket);

	CRMCustmemberBasicInfo loginWithPwd(String mobile,
			String password);

	int logoutByTicket(String ticket);
	
	int modifyPassword(String ticket, String oldPassword, String newPassword);
	
	CRMCustmemberBasicInfo registerNewUser(String mobile, String password, String name, String sex, String mkt, String referee);
	
	int modifyUserInfo(String ticket);
	
	String getValidCRMAccessToken();
	
	String getValidCRMCustmemberTokenByTicket(String cmmemid);
	
	String getValidCRMCustmemberTokenByMemId(String cmmemid);
	
	
	
	
	
}
