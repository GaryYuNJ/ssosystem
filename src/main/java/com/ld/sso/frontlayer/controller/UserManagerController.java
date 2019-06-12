package com.ld.sso.frontlayer.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.mapper.util.StringUtil;

import com.alibaba.fastjson.JSONArray;
import com.ld.sso.crm.domain.CRMCustmemberModel;
import com.ld.sso.crm.properties.CRMInterfaceProperties;
import com.ld.sso.frontlayer.databean.CommonRequestParam;
import com.ld.sso.frontlayer.databean.CommonResponseInfo;
import com.ld.sso.frontlayer.util.SignVerification;
import com.ld.sso.midlayer.databean.CRMCustmemberBasicInfo;
import com.ld.sso.midlayer.service.IuserInfoService;
import com.ld.sso.midlayer.service.SMSService;

@RestController
@RequestMapping(value = "RestAPI")
public class UserManagerController {
	Logger logger = LogManager.getLogger(this.getClass());
	
	@Resource
	private IuserInfoService userInfoService;
	@Autowired
	SMSService smsService;
	@Autowired 
	private CRMInterfaceProperties crmInterfaceProperties; 

	@RequestMapping(value = "/{interfaceCode}", method = { RequestMethod.POST }, 
			produces = "application/json; charset=utf-8")
	//sno 设备识别号
	public CommonResponseInfo userInfoManager(@PathVariable String interfaceCode, 
			@RequestBody CommonRequestParam param, String sno) {
		
		logger.warn("~~~userInfoManager()~~~~sno:{}", sno);
		logger.warn("~~~userInfoManager()~~~~interfaceCode:{}", interfaceCode);
		logger.warn("~~~userInfoManager()~~~~param:{}", JSONArray.toJSON(param));
		//验证签名
		if(this.signVerification(param)){
			return this.processByInterfaceCode(interfaceCode, param);
		}else{
			CommonResponseInfo response = new CommonResponseInfo();
			response.setCode("9007");
			response.setMsg("数据的签名信息错误,属于非法访问");
			logger.warn("~~~userInfoManager()~~~~数据的签名信息错误,属于非法访问");
			return response;
		}
	}
	
	public boolean signVerification(CommonRequestParam param){
		logger.info("~~~signVerification()~~~~");
		String sign = SignVerification.getSha1(param.getTimestamp() + crmInterfaceProperties.getSignKey());
		
		return sign.equals(param.getSign());
	}
    
	public CommonResponseInfo processByInterfaceCode(String interfaceCode, CommonRequestParam param){
		
		if(interfaceCode.equals(crmInterfaceProperties.getLoginCode())){
			return this.loginWithPwd(param);
		}else if(interfaceCode.equals(crmInterfaceProperties.getModifyPasswdCode())){
			return this.modifyPassword(param);
		}else if(interfaceCode.equals(crmInterfaceProperties.getNewPasswordCode())){
			return this.setNewPassword(param);
		}else if(interfaceCode.equals(crmInterfaceProperties.getModifyUserinfoCode())){
			return this.modifyUserInfo(param);
		}else if(interfaceCode.equals(crmInterfaceProperties.getQueryuserBasicinfoCode())){
			return this.queryUserBasicInfoByTicket(param);
		}else if(interfaceCode.equals(crmInterfaceProperties.getQueryuserinfoCode())){
			return this.queryUserFullInfoByTicket(param);
		}else if(interfaceCode.equals(crmInterfaceProperties.getLogoutCode())){
			return this.loginOut(param);
		}else if(interfaceCode.equals(crmInterfaceProperties.getQueryJFBalanceCode())){
			return this.queryJFBalance(param);
		}else if(interfaceCode.equals(crmInterfaceProperties.getChangeJFCode())){
			return this.changeCusJF(interfaceCode,param);
		}else if(interfaceCode.equals(crmInterfaceProperties.getQueryJFSummaryCode())){
			return this.queryJFSummary(param);
		}else if(interfaceCode.equals(crmInterfaceProperties.getQueryJFHistoryCode())){
			return this.queryJFHistoryList(param);
		}else if(interfaceCode.equals(crmInterfaceProperties.getSendVerifyCode())){
			return this.sendVerifySMSCode(param);
		}else if(interfaceCode.equals(crmInterfaceProperties.getVerifyCode())){
			return this.verifySMSCode(param);
		}else if(interfaceCode.equals(crmInterfaceProperties.getMigrateMobile())){
			return this.migrateMobile(param);
		}else if(interfaceCode.equals(crmInterfaceProperties.getCheckMobileRegStatus())){
			return this.checkMobileRegStatus(param);
		}else if(interfaceCode.equals(crmInterfaceProperties.getQueryusersimpleinfoCode())){
			return this.queryUserSimpleInfo(param);
		}else if(interfaceCode.equals(crmInterfaceProperties.getQuerysimpleinfoByMemid())){
			return this.querysimpleinfoByMemid(param);
		}else{
			return userInfoService.sendCommonRequestToCRM(interfaceCode, param);
		}
	}
	

	//扣减积分，两种方式。1. 用户登录，有ticket; 2. pop后台job调用，只有cmmemberId
	public CommonResponseInfo changeCusJF(String interfaceCode, CommonRequestParam param){
		logger.warn("~~~changeCusJF~~~JSONArray.toJSON(param):{}", JSONArray.toJSON(param));
		
		if(null != param && null != param.getParams()
				&& ((null != param.getParams().get("ticket") && StringUtil.isNotEmpty(param.getParams().get("ticket").toString()))
						|| (null != param.getParams().get("cmmemberId") && StringUtil.isNotEmpty(param.getParams().get("cmmemberId").toString())))){
			//有ticket, 走通用接口
			if(null != param.getParams().get("ticket") && StringUtil.isNotEmpty(param.getParams().get("ticket").toString())){
				return userInfoService.sendCommonRequestToCRM(interfaceCode, param);
			//只有cmmemberId，
			}else{
				return userInfoService.changeCusJFByMemId(interfaceCode, param);
			}
		}
		
		CommonResponseInfo response = new CommonResponseInfo();
		response.setCode("9004");
		response.setMsg("无效参数或不符合JSON格式规范");
		return response;
		
	}
	
	
	public CommonResponseInfo loginOut(CommonRequestParam param){
		
		if(null != param && null != param.getParams()
				&& null != param.getParams().get("ticket") && StringUtil.isNotEmpty(param.getParams().get("ticket").toString())){
		
			return userInfoService.logoutByTicket(param.getParams().get("ticket").toString(), param.getSource());
		}
		
		CommonResponseInfo response = new CommonResponseInfo();
		response.setCode("9004");
		response.setMsg("无效参数或不符合JSON格式规范");
		return response;
	}
	
	public CommonResponseInfo loginWithPwd(CommonRequestParam param){
		
		if(null != param && null != param.getParams()
				&& null != param.getParams().get("p_mobile") && StringUtil.isNotEmpty(param.getParams().get("p_mobile").toString())
				&& null != param.getParams().get("p_pwd") && StringUtil.isNotEmpty(param.getParams().get("p_pwd").toString())){
		
			return userInfoService.loginWithPwd(param.getParams().get("p_mobile").toString(), param.getParams().get("p_pwd").toString(), param.getSource());
		}
		
		CommonResponseInfo response = new CommonResponseInfo();
		response.setCode("9004");
		response.setMsg("无效参数或不符合JSON格式规范");
		return response;
		
	}
	
	public CommonResponseInfo queryJFBalance(CommonRequestParam param){
		
		if(null != param && null != param.getParams()
				&& null != param.getParams().get("ticket") && StringUtil.isNotEmpty(param.getParams().get("ticket").toString())){
		
			return userInfoService.queryJFBalance(param.getParams().get("ticket").toString());
		}
		
		CommonResponseInfo response = new CommonResponseInfo();
		response.setCode("9004");
		response.setMsg("无效参数或不符合JSON格式规范");
		return response;
	}

	//查询用户概要。包含积分余额，总历史消费积分，总历史获取积分
	public CommonResponseInfo queryJFSummary(CommonRequestParam param){
		
		if(null != param && null != param.getParams()
				&& null != param.getParams().get("ticket") && StringUtil.isNotEmpty(param.getParams().get("ticket").toString())){
		
			return userInfoService.queryJFSummary(param.getParams().get("ticket").toString());
		}
		
		CommonResponseInfo response = new CommonResponseInfo();
		response.setCode("9004");
		response.setMsg("无效参数或不符合JSON格式规范");
		return response;
	}
	
	//根据条件查询积分记录
	public CommonResponseInfo queryJFHistoryList(CommonRequestParam param){
		
		if(null != param && null != param.getParams()
				&& null != param.getParams().get("ticket") && StringUtil.isNotEmpty(param.getParams().get("ticket").toString())){
		
			int pageSize = 10;
			int startRow = 1;
			int type = 0;//所有的
			if(null != param.getParams().get("pageSize") && StringUtil.isNotEmpty(param.getParams().get("pageSize").toString())){
				pageSize = Integer.parseInt(param.getParams().get("pageSize").toString());
			}
			if(null != param.getParams().get("pageNo") && StringUtil.isNotEmpty(param.getParams().get("pageNo").toString())){
				int pageNo = Integer.parseInt(param.getParams().get("pageNo").toString());
				startRow = (pageNo-1)*pageSize+1;
			}
			if(null != param.getParams().get("type") && StringUtil.isNotEmpty(param.getParams().get("type").toString())){
				type = Integer.parseInt(param.getParams().get("type").toString());
			}
			return userInfoService.queryJFHistoryList(param.getParams().get("ticket").toString(), startRow, pageSize, type);
		}
		
		CommonResponseInfo response = new CommonResponseInfo();
		response.setCode("9004");
		response.setMsg("无效参数或不符合JSON格式规范");
		return response;
	}
	
	//查询积分增加记录
	public CommonResponseInfo queryJFAddList(CommonRequestParam param){
		
		if(null != param && null != param.getParams()
				&& null != param.getParams().get("ticket") && StringUtil.isNotEmpty(param.getParams().get("ticket").toString())){
		
			int pageSize = 10;
			int startRow = 1;
			if(null != param.getParams().get("startRow") && StringUtil.isNotEmpty(param.getParams().get("startRow").toString())){
				startRow = Integer.parseInt(param.getParams().get("startRow").toString());
			}
			return userInfoService.queryJFHistoryList(param.getParams().get("ticket").toString(), startRow, pageSize, 1);
		}
		
		CommonResponseInfo response = new CommonResponseInfo();
		response.setCode("9004");
		response.setMsg("无效参数或不符合JSON格式规范");
		return response;
	}
	
	//查询积分扣减记录
	public CommonResponseInfo queryJFSubList(CommonRequestParam param){
		
		if(null != param && null != param.getParams()
				&& null != param.getParams().get("ticket") && StringUtil.isNotEmpty(param.getParams().get("ticket").toString())){
		
			int pageSize = 10;
			int startRow = 1;
			if(null != param.getParams().get("startRow") && StringUtil.isNotEmpty(param.getParams().get("startRow").toString())){
				startRow = Integer.parseInt(param.getParams().get("startRow").toString());
			}
			return userInfoService.queryJFHistoryList(param.getParams().get("ticket").toString(), startRow, pageSize, -1);
		}
		
		CommonResponseInfo response = new CommonResponseInfo();
		response.setCode("9004");
		response.setMsg("无效参数或不符合JSON格式规范");
		return response;
	}
	
	
	public CommonResponseInfo queryUserBasicInfoByTicket(CommonRequestParam param){
		
		if(null != param && null != param.getParams()
				&& null != param.getParams().get("ticket") && StringUtil.isNotEmpty(param.getParams().get("ticket").toString())){
		
			return userInfoService.queryUserBasicInfoByTicket(param.getParams().get("ticket").toString());
		}
		
		CommonResponseInfo response = new CommonResponseInfo();
		response.setCode("9004");
		response.setMsg("无效参数或不符合JSON格式规范");
		return response;
	}
	
	
	public CommonResponseInfo queryUserFullInfoByTicket(CommonRequestParam param){
		
		if(null != param && null != param.getParams()
				&& null != param.getParams().get("ticket") && StringUtil.isNotEmpty(param.getParams().get("ticket").toString())){
		
			return userInfoService.queryUserFullInfoByTicket(param.getParams().get("ticket").toString());
		}
		
		CommonResponseInfo response = new CommonResponseInfo();
		response.setCode("9004");
		response.setMsg("无效参数或不符合JSON格式规范");
		return response;
	}

	public CommonResponseInfo queryUserSimpleInfo(CommonRequestParam param){
		
		if(null != param && null != param.getParams()
				&& null != param.getParams().get("ticket") && StringUtil.isNotEmpty(param.getParams().get("ticket").toString())){
		
			return userInfoService.queryUserSimpleInfo(param.getParams().get("ticket").toString());
		}
		
		CommonResponseInfo response = new CommonResponseInfo();
		response.setCode("9004");
		response.setMsg("无效参数或不符合JSON格式规范");
		return response;
	}
	
	public CommonResponseInfo querysimpleinfoByMemid(CommonRequestParam param){
		
		if(null != param && null != param.getParams()
				&& null != param.getParams().get("cmmemid") && StringUtil.isNotEmpty(param.getParams().get("cmmemid").toString())){
		
			return userInfoService.querysimpleinfoByMemid(param.getParams().get("cmmemid").toString());
		}
		
		CommonResponseInfo response = new CommonResponseInfo();
		response.setCode("9004");
		response.setMsg("无效参数或不符合JSON格式规范");
		return response;
	}
	
	public CommonResponseInfo modifyPassword(CommonRequestParam param){
		
		if(null != param && null != param.getParams()
				&& null != param.getParams().get("ticket") && StringUtil.isNotEmpty(param.getParams().get("ticket").toString())
				&& null != param.getParams().get("p_oldpwd") && StringUtil.isNotEmpty(param.getParams().get("p_oldpwd").toString())
				&& null != param.getParams().get("p_newpwd") && StringUtil.isNotEmpty(param.getParams().get("p_newpwd").toString())){
		
			return userInfoService.modifyPassword(param.getParams().get("ticket").toString(), 
					param.getParams().get("p_oldpwd").toString(), param.getParams().get("p_newpwd").toString());
		}
		
		CommonResponseInfo response = new CommonResponseInfo();
		response.setCode("9004");
		response.setMsg("无效参数或不符合JSON格式规范");
		return response;
		
	}
	
	public CommonResponseInfo setNewPassword(CommonRequestParam param){
		
		if(null != param && null != param.getParams()
				&& null != param.getParams().get("mobile") && StringUtil.isNotEmpty(param.getParams().get("mobile").toString())
				&& null != param.getParams().get("p_newpwd") && StringUtil.isNotEmpty(param.getParams().get("p_newpwd").toString())){
		
			return userInfoService.setNewPassword(param.getParams().get("mobile").toString(), 
					param.getParams().get("p_newpwd").toString());
		}
		
		CommonResponseInfo response = new CommonResponseInfo();
		response.setCode("9004");
		response.setMsg("无效参数或不符合JSON格式规范");
		return response;
		
	}
	
	
	public CommonResponseInfo modifyUserInfo(CommonRequestParam param){
		
		if(null != param && null != param.getParams()
				&& null != param.getParams().get("ticket") && StringUtil.isNotEmpty(param.getParams().get("ticket").toString())
				&& ((null != param.getParams().get("p_name") && StringUtil.isNotEmpty(param.getParams().get("p_name").toString()))
					|| (null != param.getParams().get("p_sex") && StringUtil.isNotEmpty(param.getParams().get("p_sex").toString()))
					|| (null != param.getParams().get("p_birth") && StringUtil.isNotEmpty(param.getParams().get("p_birth").toString()))
					|| (null != param.getParams().get("p_cmaddr") && StringUtil.isNotEmpty(param.getParams().get("p_cmaddr").toString()))
					|| (null != param.getParams().get("p_cmemail") && StringUtil.isNotEmpty(param.getParams().get("p_cmemail").toString()))
					|| (null != param.getParams().get("p_cmptname") && StringUtil.isNotEmpty(param.getParams().get("p_cmptname").toString()))
						)
					){
			
			CRMCustmemberModel cusModel = new CRMCustmemberModel();
			
			cusModel.setCmname(null != param.getParams().get("p_name")?param.getParams().get("p_name").toString():null);
			cusModel.setCmsex(null != param.getParams().get("p_sex")?param.getParams().get("p_sex").toString():null);
			cusModel.setCmaddr(null != param.getParams().get("p_cmaddr")?param.getParams().get("p_cmaddr").toString():null);
			cusModel.setCmemail(null != param.getParams().get("p_cmemail")?param.getParams().get("p_cmemail").toString():null);
			cusModel.setCmptname(null != param.getParams().get("p_cmptname")?param.getParams().get("p_cmptname").toString():null);
			
			if(null != param.getParams().get("p_birth") && StringUtil.isNotEmpty(param.getParams().get("p_birth").toString())){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					cusModel.setCmbirthday(format.parse(param.getParams().get("p_birth").toString()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return userInfoService.modifyUserInfo(param.getParams().get("ticket").toString(), cusModel);
		}
		
		CommonResponseInfo response = new CommonResponseInfo();
		response.setCode("9004");
		response.setMsg("无效参数或不符合JSON格式规范");
		return response;
	}
	
	
	@RequestMapping(value = "/queryUserInfoForEvcard", method = { RequestMethod.GET, RequestMethod.POST }, 
			produces = "application/json; charset=utf-8")
	public Map<String,String> queryUserInfoForEvcard(@RequestParam String token) {
		Map<String,String> response = new HashMap<String,String>();
		
		logger.info("~~~evcardUserInfo()~~~~token:{}", token);
		if(StringUtil.isNotEmpty(token)){
			CRMCustmemberBasicInfo userInfo =  userInfoService.queryUserBasicInfoForEvcard(token);
			
			if(null != userInfo){
				response.put("mobileNo", userInfo.getCmmobile());
				response.put("message","OK");
				response.put("status","0");
				logger.info("~~~evcardUserInfo()~~~~mobileNo:{}", userInfo.getCmmobile());
			}else{
				response.put("message","token is not available.");
				response.put("status","-1");
				logger.info("~~~evcardUserInfo()~~~~warning: token is not available");
			}
		}else{
			response.put("message","token is not available.");
			response.put("status","-1");
			logger.info("~~~evcardUserInfo()~~~~warning: token is not available");
		}
		
		return response;
	}
	
	@RequestMapping(value = "/sendVerifySMSCode", method = { RequestMethod.POST }, 
			produces = "application/json; charset=utf-8")
	public CommonResponseInfo sendVerifySMSCode(CommonRequestParam param){
		
		if(null != param && null != param.getParams()
				&& null != param.getParams().get("ticket") && StringUtil.isNotEmpty(param.getParams().get("ticket").toString())
						&& null != param.getParams().get("mobile") && StringUtil.isNotEmpty(param.getParams().get("mobile").toString())
				){
		
			return smsService.sendVerifyCode(param.getParams().get("ticket").toString(), param.getParams().get("mobile").toString());
		}
		
		CommonResponseInfo response = new CommonResponseInfo();
		response.setCode("9004");
		response.setMsg("无效参数或不符合JSON格式规范");
		return response;
		
	}
	
	@RequestMapping(value = "/verifySMSCode", method = { RequestMethod.POST }, 
			produces = "application/json; charset=utf-8")
	public CommonResponseInfo verifySMSCode(CommonRequestParam param){
		
		if(null != param && null != param.getParams()
				&& null != param.getParams().get("smsCode") 
				&& StringUtil.isNotEmpty(param.getParams().get("smsCode").toString())
				&& null != param.getParams().get("msgId") 
				&& StringUtil.isNotEmpty(param.getParams().get("msgId").toString())){
		
			return smsService.verifySMSCode(param.getParams().get("smsCode").toString(), param.getParams().get("msgId").toString());
		}
		
		CommonResponseInfo response = new CommonResponseInfo();
		response.setCode("9004");
		response.setMsg("无效参数或不符合JSON格式规范");
		return response;
	}
	

	@RequestMapping(value = "/migrateMobile", method = { RequestMethod.POST }, 
			produces = "application/json; charset=utf-8")
	private CommonResponseInfo migrateMobile(CommonRequestParam param) {
		if(null != param && null != param.getParams()
				&& null != param.getParams().get("smsCode") 
				&& StringUtil.isNotEmpty(param.getParams().get("smsCode").toString())
				&& null != param.getParams().get("msgId")
				&& StringUtil.isNotEmpty(param.getParams().get("msgId").toString())
				&& null != param.getParams().get("ticket") 
				&& StringUtil.isNotEmpty(param.getParams().get("ticket").toString())
				&& null != param.getParams().get("newMobile") 
				&& StringUtil.isNotEmpty(param.getParams().get("newMobile").toString())){
		
			return userInfoService.migrateMobile(param.getParams().get("smsCode").toString(), 
					param.getParams().get("msgId").toString(), param.getParams().get("ticket").toString(),param.getParams().get("newMobile").toString());
		}
		
		CommonResponseInfo response = new CommonResponseInfo();
		response.setCode("9004");
		response.setMsg("无效参数或不符合JSON格式规范");
		return response;
	}
	
	@RequestMapping(value = "/checkMobileRegStatus", method = { RequestMethod.POST }, 
			produces = "application/json; charset=utf-8")
	private CommonResponseInfo checkMobileRegStatus(CommonRequestParam param) {
		if(null != param && null != param.getParams()
				&& null != param.getParams().get("ticket") 
				&& StringUtil.isNotEmpty(param.getParams().get("ticket").toString())
				&& null != param.getParams().get("newMobile") 
				&& StringUtil.isNotEmpty(param.getParams().get("newMobile").toString())){
		
			return userInfoService.checkMobileRegStatus(param.getParams().get("ticket").toString(),param.getParams().get("newMobile").toString());
		}
		
		CommonResponseInfo response = new CommonResponseInfo();
		response.setCode("9004");
		response.setMsg("无效参数或不符合JSON格式规范");
		return response;
	}
}
