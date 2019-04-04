package com.ld.sso.midlayer.service;

import com.ld.sso.frontlayer.databean.CommonResponseInfo;

public interface SMSService {
	
	CommonResponseInfo sendVerifyCodeByTicket(String ticket);
	
	CommonResponseInfo sendVerifyCodeByMobile(String mobile);

	CommonResponseInfo verifySMSCode(String smsCode, String msgId);

}
