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

@RestController
@RequestMapping(value = "RestAPI")
public class UserManagerController {
	Logger logger = LogManager.getLogger(this.getClass());
	
	@Resource
	private IuserInfoService userInfoService;
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
	
}
