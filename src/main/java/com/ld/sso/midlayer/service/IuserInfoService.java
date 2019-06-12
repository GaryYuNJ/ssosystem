package com.ld.sso.midlayer.service;

import com.ld.sso.crm.domain.CRMCustmemberModel;
import com.ld.sso.frontlayer.databean.CommonRequestParam;
import com.ld.sso.frontlayer.databean.CommonResponseInfo;
import com.ld.sso.midlayer.databean.CRMCustmemberBasicInfo;

public interface IuserInfoService {

	CommonResponseInfo queryUserBasicInfoByTicket(String ticket);

	CommonResponseInfo loginWithPwd(String mobile,
			String password, String source);

	CommonResponseInfo modifyPassword(String ticket, String oldPassword, String newPassword);
	
//	CommonResponseInfo registerNewUser(String mobile, String password, String name, String sex, String mkt, String referee);
	
	String getValidCRMAccessToken();
	
//	String getValidCRMCustmemberTokenByTicket(String ticket);
//	
//	String getValidCRMCustmemberTokenByMemId(String cmmemid);

	String getNewSSOTicket(String cmmemId, String source,
			CRMCustmemberBasicInfo basicInfo);

	CommonResponseInfo queryUserFullInfoByTicket(String ticket);

	CommonResponseInfo logoutByTicket(String ticket, String source);

	CommonResponseInfo modifyUserInfo(String ticket, CRMCustmemberModel cusModel);

	CommonResponseInfo sendCommonRequestToCRM(String crmInterfaceCode,
			CommonRequestParam requestparam);

	CommonResponseInfo queryJFBalance(String ticket);

	CommonResponseInfo setNewPassword(String mobile, String newPassword);

	CommonResponseInfo changeCusJFByMemId(String crmInterfaceCode,
			CommonRequestParam requestparam);

	CRMCustmemberBasicInfo queryUserBasicInfoForEvcard(String ticket);

	CommonResponseInfo queryJFSummary(String ticket);


	CommonResponseInfo queryJFHistoryList(String ticket, int startRow,
			int pageSize, int type);

	CommonResponseInfo queryJFBalanceByCMcustid(String mobile, String cmcustid);

	CommonResponseInfo migrateMobile(String smsCode, String msgId, String ticket, String newMobile);

	CommonResponseInfo checkMobileRegStatus(String string, String string2);

	CommonResponseInfo queryUserSimpleInfo(String ticket);

	CommonResponseInfo querysimpleinfoByMemid(String cmmemid);
	 
}
