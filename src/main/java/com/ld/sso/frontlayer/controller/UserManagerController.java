package com.ld.sso.frontlayer.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
			@RequestBody CommonRequestParam param, @RequestParam String sno) {
		
		logger.info("~~~userInfoManager()~~~~sno:{}", sno);
		logger.info("~~~userInfoManager()~~~~interfaceCode:{}", interfaceCode);
		logger.info("~~~userInfoManager()~~~~param:{}", JSONArray.toJSON(param));
		//验证签名
		if(this.signVerification(param)){
			return this.processByInterfaceCode(interfaceCode, param);
		}else{
			CommonResponseInfo response = new CommonResponseInfo();
			response.setCode("9007");
			response.setMsg("数据的签名信息错误,属于非法访问");
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
		}else if(interfaceCode.equals(crmInterfaceProperties.getModifyUserinfoCode())){
			return this.modifyUserInfo(param);
		}else if(interfaceCode.equals(crmInterfaceProperties.getQueryuserBasicinfoCode())){
			return this.queryUserBasicInfoByTicket(param);
		}else if(interfaceCode.equals(crmInterfaceProperties.getQueryuserinfoCode())){
			return this.queryUserFullInfoByTicket(param);
		}else if(interfaceCode.equals(crmInterfaceProperties.getLogoutCode())){
			return this.loginOut(param);
		}else{
			return userInfoService.sendCommonRequestToCRM(interfaceCode, param);
		}
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
			cusModel.setCmcompany(null != param.getParams().get("p_cmptname")?param.getParams().get("p_cmptname").toString():null);
			
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
	
}
