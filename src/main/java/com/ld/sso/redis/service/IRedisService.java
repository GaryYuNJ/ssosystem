package com.ld.sso.redis.service;

import com.ld.sso.crm.databean.CRMAccessTokenInfo;
import com.ld.sso.midlayer.databean.CRMCustmemberBasicInfo;

/**
 */
public interface IRedisService {
    /*请像下面的代码一样定义*/
    //void textfun();

	CRMCustmemberBasicInfo searchUserInfoByTicket(String ticket);

	CRMCustmemberBasicInfo saveUserInfoInCache(String ticket,
			CRMCustmemberBasicInfo userinfo);

	void deleteUserInfoInCacheByTicket(String ticket);
	
	CRMAccessTokenInfo saveCRMAccessToken(CRMAccessTokenInfo accessTokenInfo);
	
	CRMAccessTokenInfo getCRMAccessToken();
	
	String saveCRMUserToken(String ticket, String userToken);
	
	String getCRMUserToken(String ticket);
}
