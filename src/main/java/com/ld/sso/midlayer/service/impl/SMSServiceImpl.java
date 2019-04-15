package com.ld.sso.midlayer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.util.StringUtil;

import com.ld.sso.core.mapper.SmsCodeMapper;
import com.ld.sso.core.model.SmsCode;
import com.ld.sso.frontlayer.databean.CommonResponseInfo;
import com.ld.sso.frontlayer.util.MessageTool;
import com.ld.sso.midlayer.databean.CRMCustmemberBasicInfo;
import com.ld.sso.midlayer.service.SMSService;
import com.ld.sso.redis.service.IRedisService;

@Service
public class SMSServiceImpl implements SMSService {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	SmsCodeMapper smsCodeMapper;
	
	@Resource
    private IRedisService redisService;
	
	@Override
	public CommonResponseInfo sendVerifyCodeByTicket(String ticket) {
		final String methodName = "sendVerifyCodeByTicket()";
		CommonResponseInfo response = new CommonResponseInfo();
		try{
			
			CRMCustmemberBasicInfo basicInfo = redisService.getUserInfoByTicket(ticket);
			if(null != basicInfo && StringUtil.isNotEmpty(basicInfo.getCmmemid())){
				
				response = this.sendVerifyCodeByMobile(basicInfo.getCmmobile());
				
			}else{
				response.setCode("9909");
				response.setMsg("ticket 不存在");
			}
		}catch(Exception e){
			response.setCode("9901");
			response.setMsg("系统异常");
			logger.error("~~~"+methodName+"~~~exception~~",e);
		}
		// TODO Auto-generated method stub
		return response;
	}

	@Override
	public CommonResponseInfo sendVerifyCode(String ticket, String mobile) {
		final String methodName = "sendVerifyCode()";
		CommonResponseInfo response = new CommonResponseInfo();
		try{
			//验证ticket
			CRMCustmemberBasicInfo basicInfo = redisService.getUserInfoByTicket(ticket);
			if(null != basicInfo && StringUtil.isNotEmpty(basicInfo.getCmmemid())){
				
				response = this.sendVerifyCodeByMobile(mobile);
				
			}else{
				response.setCode("9909");
				response.setMsg("ticket 不存在");
			}
		}catch(Exception e){
			response.setCode("9901");
			response.setMsg("系统异常");
			logger.error("~~~"+methodName+"~~~exception~~",e);
		}
		// TODO Auto-generated method stub
		return response;
	}

	@Override
	public CommonResponseInfo sendVerifyCodeByMobile(String mobile) {
		final String methodName = "sendVerifyCodeByTicket()";
		CommonResponseInfo response = new CommonResponseInfo();
		logger.info("~~~"+methodName+"~~~start~~~");
		
		try{
			String code = (int)((Math.random()*9+1)*1000)+"";//4位随机数
			logger.info("~~~sendSMSCode~~~end~~~mobile: "+mobile);
			logger.info("~~~sendSMSCode~~~end~~~code: "+code);
			SmsCode shimaoSmsCode = new SmsCode();
			
			shimaoSmsCode.setCode(code);
			shimaoSmsCode.setCreateTime(new Date());
			shimaoSmsCode.setMobile(mobile);
			shimaoSmsCode.setState(0);
			
			//插入数据库，返回messageid
			smsCodeMapper.insert(shimaoSmsCode);
			
			SmsCode shimaoSms = smsCodeMapper.selectValidByMobileAndCode(mobile, code);
			
			Map<String,Object> result = new HashMap<String,Object>();
			MessageTool.sendVerifyCodeMsg(code, mobile);
			
			result.put("msgId", shimaoSms.getId()+"");
			response.setData(result);
			response.setCode("0");
			
			logger.info("~~~"+methodName+"~~~messageId: "+shimaoSms.getId());
			
		}catch(Exception e){
			response.setCode("9901");
			response.setMsg("系统异常");
			logger.error("~~~~~~send verify code error~~~~~~",e);
			e.printStackTrace();
		}
		
		logger.info("~~~"+methodName+"~~~~~end~~~~~~~~~");
		
		return response;
	}

	@Override
	public CommonResponseInfo verifySMSCode(String smsCodeStr, String msgId) {
		final String methodName = "verifySMSCode()";
		CommonResponseInfo response = new CommonResponseInfo();
		logger.info("~~~"+methodName+"~~~start~~~");
		
		try{
			Map<String,Object> result = new HashMap<String,Object>();
			SmsCode smsCode = smsCodeMapper.selectByPrimaryKey(Long.parseLong(msgId));
			if(null != smsCode  && smsCode.getCode().equals(smsCodeStr)){
				response.setMsg("验证成功");
				response.setCode("0");
			}else{
				response.setMsg("验证失败");
				response.setCode("-1");
			}
			
		}catch(Exception e){
			response.setCode("9901");
			response.setMsg("系统异常");
			logger.error("~~~~~~ verify code error~~~~~~",e);
			e.printStackTrace();
		}
		
		logger.info("~~~"+methodName+"~~~~~end~~~~~~~~~");
		
		return response;
	}
	

	@Override
	public SmsCode queryByCodeAndMsgId(String smsCodeStr, String msgId) {
		final String methodName = "queryByCodeAndMsgId()";
		logger.info("~~~"+methodName+"~~~start~~~");
		
		try{
			SmsCode smsCode = smsCodeMapper.selectByPrimaryKey(Long.parseLong(msgId));
			if(null != smsCode && smsCodeStr.equals(smsCode.getCode())){
				return smsCode;
			}
		}catch(Exception e){
			logger.error("~~~~~~ verify code error~~~~~~",e);
			e.printStackTrace();
		}
		
		logger.info("~~~"+methodName+"~~~~~end~~~~~~~~~");
		
		return null;
	}

}
