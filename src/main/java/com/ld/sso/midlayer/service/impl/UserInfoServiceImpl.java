package com.ld.sso.midlayer.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.util.StringUtil;

import com.alibaba.fastjson.JSONArray;
import com.ld.sso.crm.databean.CRMAccessTokenInfo;
import com.ld.sso.crm.databean.ResponseFromCRMData;
import com.ld.sso.crm.domain.CRMCustmemberModel;
import com.ld.sso.crm.service.ICRMInterfaceService;
import com.ld.sso.frontlayer.databean.CommonRequestParam;
import com.ld.sso.frontlayer.databean.CommonResponseInfo;
import com.ld.sso.midlayer.databean.CRMCustmemberBasicInfo;
import com.ld.sso.midlayer.databean.CRMCustmemberFullInfo;
import com.ld.sso.midlayer.dataconvert.CustModelToFullInfoConverter;
import com.ld.sso.midlayer.service.IuserInfoService;
import com.ld.sso.redis.service.IRedisService;

@Service
public class UserInfoServiceImpl implements IuserInfoService {
	Logger logger = LogManager.getLogger(this.getClass());
	
	@Resource
    private IRedisService redisService;
	
	@Resource
	private ICRMInterfaceService cRMInterfaceService;
	
	@Resource
	private CustModelToFullInfoConverter custModelToFullInfoConverter;
	

	@Override
	public CommonResponseInfo loginWithPwd(String mobile, String password, String source) {
		logger.info("~~~loginWithPwd()~~~start~~mobile:{},password:{},source:{}", mobile, password,source);
		CommonResponseInfo responseInfo = new CommonResponseInfo();
		try{
			
			CRMCustmemberModel cusModel = cRMInterfaceService.authUserLogin(mobile, password);
			
			if(null != cusModel && StringUtil.isNotEmpty(cusModel.getCmmemid())){
				CRMCustmemberBasicInfo basicInfo = new CRMCustmemberBasicInfo();
				basicInfo.setCmcustid(cusModel.getCmcustid());
				basicInfo.setCmmemid(cusModel.getCmmemid());
				basicInfo.setCmmobile(cusModel.getCmmobile1());
				
				responseInfo.setCustmemberBasicInfo(basicInfo);
				responseInfo.setCode("0");
				responseInfo.setMsg("");
				responseInfo.setTicket(this.getNewSSOTicket(cusModel.getCmmemid(), source, basicInfo));
			}else{
				responseInfo.setCode("2");
				responseInfo.setMsg("用户不存在或者密码错误");
			}
			
		}catch(Exception e){
			responseInfo.setCode("1");
			responseInfo.setMsg("系统异常");
			logger.error("~~~loginWithPwd()~~~exception~~",e);
		}
		
		logger.info("~~~loginWithPwd()~~~end~~");
		return responseInfo;
	}

	@Override
	public String getNewSSOTicket(String cmmemId, String source, CRMCustmemberBasicInfo basicInfo) {
		final String methodName = "getNewSSOTicket()";
		
		logger.info("~~~"+methodName+"~~~start~~cmmemId:{},source:{}", cmmemId, source);
		//生成四位随机数
		int random = (int)(Math.random()*(9999-1000+1))+1000;
		String newTicket = cmmemId+random+"_"+source;
		String memIdToTicketCacheKey = cmmemId+"_"+source;
		
		//更新到redis
		//先删除source之前的ticket缓存
		//通过cmmemId_source为key找到对应的ticket信息
		String oldTicket = redisService.getMemIdToTicketCache(memIdToTicketCacheKey);
		
		//删除ticket为Key，对应的用户信息
		if(StringUtil.isNotEmpty(oldTicket)){
			redisService.deleteUserInfoInCacheByTicket(oldTicket);
		}
		//保存ticket为Key，对应的用户信息
		redisService.saveUserInfoInCache(newTicket, basicInfo);
		//更新cmmemId_source为key找到对应的ticket信息
		redisService.saveMemIdToTicketCache(memIdToTicketCacheKey, newTicket);
		logger.info("~~~"+methodName+"~~~end~~cmmemId:{},source:{}", cmmemId, source);
		return newTicket;
	}
	
	@Override
	public CommonResponseInfo queryUserBasicInfoByTicket(String ticket) {
		final String methodName = "queryUserBasicInfoByTicket()";
		logger.info("~~~"+methodName+"~~~start~~ticket:{}",ticket);
		CommonResponseInfo response = new CommonResponseInfo();
		try{
			
			CRMCustmemberBasicInfo basicInfo = redisService.getUserInfoByTicket(ticket);
			if(null != basicInfo && StringUtil.isNotEmpty(basicInfo.getCmmemid())){
				response.setCustmemberBasicInfo(basicInfo);
				response.setCode("0");
			}else{
				response.setCode("9");
				response.setMsg("ticket 不存在");
			}
		}catch(Exception e){
			response.setCode("1");
			response.setMsg("系统异常");
			logger.error("~~~"+methodName+"~~~exception~~",e);
		}
		logger.info("~~~"+methodName+"~~~end~~");
		// TODO Auto-generated method stub
		return response;
	}

	@Override
	public CommonResponseInfo queryUserFullInfoByTicket(String ticket) {
		final String methodName = "queryUserFullInfoByTicket()";
		
		logger.info("~~~"+methodName+"~~~start~~ticket:{}",ticket);
		CommonResponseInfo response = new CommonResponseInfo();
		try{
			
			CRMCustmemberBasicInfo basicInfo = redisService.getUserInfoByTicket(ticket);
			
			if(null != basicInfo && StringUtil.isNotEmpty(basicInfo.getCmmemid())){
				//查询数据库，获取fullInfo
				CRMCustmemberFullInfo fullInfo = new CRMCustmemberFullInfo();
				CRMCustmemberModel cusModel = cRMInterfaceService.getFullInfoByPrimaryKey(basicInfo.getCmmemid());
				if(null != cusModel && StringUtil.isNotEmpty(cusModel.getCmmemid())){
					custModelToFullInfoConverter.convert(cusModel, fullInfo);
					response.setCode("0");
					response.setCustmemberFullInfo(fullInfo);
				}else{
					response.setCode("2");
					response.setMsg("数据库获取用户全量信息失败");
					logger.error("~~~"+methodName+"~~~get fullInfo failed~~");
				}
				response.setCustmemberBasicInfo(basicInfo);
				
			}else{
				response.setCode("9");
				response.setMsg("ticket 不存在");
			}
		}catch(Exception e){
			response.setCode("1");
			response.setMsg("系统异常");
			logger.error("~~~"+methodName+"~~~exception~~",e);
		}
		logger.info("~~~"+methodName+"~~~end~~");
		// TODO Auto-generated method stub
		return response;
	}

	@Override
	public CommonResponseInfo logoutByTicket(String ticket, String source) {
		final String methodName = "logoutByTicket()";
		
		logger.info("~~~"+methodName+"~~~start~~ticket:{}",ticket);
		CommonResponseInfo response = new CommonResponseInfo();
		try{
			//删除redis缓存；两个
			CRMCustmemberBasicInfo basicInfo = redisService.getUserInfoByTicket(ticket);
			
			if(null != basicInfo && StringUtil.isNotEmpty(basicInfo.getCmmemid())){
				
				redisService.deleteMemIdToTicketCache(basicInfo.getCmmemid()+"_"+source);
				redisService.deleteUserInfoInCacheByTicket(ticket);
				
				response.setCode("0");
			}
		}catch(Exception e){
			response.setCode("1");
			response.setMsg("系统异常");
			logger.error("~~~"+methodName+"~~~exception~~",e);
		}
		logger.info("~~~"+methodName+"~~~end~~");
		return response;
	}

	@Override
	public CommonResponseInfo modifyPassword(String ticket, String oldPassword,
			String newPassword) {
		final String methodName = "modifyPassword()";
		
		logger.info("~~~"+methodName+"~~~start~~ticket:{}",ticket);
		CommonResponseInfo response = new CommonResponseInfo();
		try{
			CRMCustmemberBasicInfo basicInfo = redisService.getUserInfoByTicket(ticket);
			
			if(null != basicInfo && StringUtil.isNotEmpty(basicInfo.getCmmemid())){
				
				CRMCustmemberModel cusModel = cRMInterfaceService.authUserLogin(basicInfo.getCmmobile(), oldPassword);
				
				if(null != cusModel && StringUtil.isNotEmpty(cusModel.getCmmemid())){
					if(cRMInterfaceService.modifyPassword(basicInfo.getCmmemid(), newPassword) > 0 ){
						response.setCode("0");
					}else{
						response.setCode("3");
						response.setMsg("密码修改失败");
					}
					
				}else{
					response.setCode("2");
					response.setMsg("原始密码不对");
				}
			}else{
				response.setCode("9");
				response.setMsg("ticket 不存在");
			}
		}catch(Exception e){
			response.setCode("1");
			response.setMsg("系统异常");
			logger.error("~~~"+methodName+"~~~exception~~",e);
		}
		logger.info("~~~"+methodName+"~~~end~~");
		
		return response;
	}

	@Override
	public CommonResponseInfo modifyUserInfo(String ticket, CRMCustmemberModel cusModel) {
		final String methodName = "modifyUserInfo()";
		
		logger.info("~~~"+methodName+"~~~start~~ticket:{}",ticket);
		CommonResponseInfo response = new CommonResponseInfo();
		try{
			CRMCustmemberBasicInfo basicInfo = redisService.getUserInfoByTicket(ticket);
			
			if(null != basicInfo && StringUtil.isNotEmpty(basicInfo.getCmmemid())){
				cusModel.setCmmemid(basicInfo.getCmmemid());
				if(cRMInterfaceService.modifyUserInfoByPrimaryKey(cusModel) > 0){
					response.setCode("0");
				}else{
					response.setCode("2");
					response.setMsg("修改失败");
				}
			}else{
				response.setCode("9");
				response.setMsg("ticket 不存在");
			}
		}catch(Exception e){
			response.setCode("1");
			response.setMsg("系统异常");
			logger.error("~~~"+methodName+"~~~exception~~",e);
		}
		logger.info("~~~"+methodName+"~~~end~~");
		
		return response;
	}

	@Override
	public String getValidCRMAccessToken() {
		
		final String methodName = "getValidCRMAccessToken()";
		
		logger.info("~~~"+methodName+"~~~start~~");
		String accessToken = "";
		
		//Access_token默认7天有效期，为了系统可用性，这里只要超过1天就重新获取
		//access_token供整个SSO平台使用，频繁获取新token对整个用户群没影响
		Date validDate=new Date((new Date()).getTime() - 1*24*3600000);
				
		CRMAccessTokenInfo accessTokenInfo = redisService.getCRMAccessToken();
		//判断是否过期
		if(null != accessTokenInfo 
				&& null != accessTokenInfo.getCreateDate()
				&& accessTokenInfo.getCreateDate().after(validDate)){
			accessToken = accessTokenInfo.getAccessToken();
		}else{
			accessToken = cRMInterfaceService.generateNewAccessToken();
			//允许一次容错
			if(StringUtil.isEmpty(accessToken) ){
				logger.info("~~~"+methodName+"~~get accessToken again");
				accessToken = cRMInterfaceService.generateNewAccessToken();
			}
			//保存到cache
			accessTokenInfo = new CRMAccessTokenInfo();
			accessTokenInfo.setAccessToken(accessToken);
			accessTokenInfo.setCreateDate(new Date());
			redisService.saveCRMAccessToken(accessTokenInfo);
		}
		// TODO Auto-generated method stub
		logger.info("~~~"+methodName+"~~~end~~accessToken:{}",accessToken);
		return accessToken;
	}
	
	//调用CRM通用接口
	@Override
	public CommonResponseInfo sendCommonRequestToCRM(String crmInterfaceCode,
			CommonRequestParam requestparam) {
		
		final String methodName = "sendCommonRequestToCRM()";
		
		logger.info("~~~"+methodName+"~~~start~~");
		
		CommonResponseInfo response =  new CommonResponseInfo();
		try{
			//解析请求入参
			Map<String, Object> params = requestparam.getParams();
			//获取ticket
			String ticket = (null != params && null != params.get("ticket")) ?
					requestparam.getParams().get("ticket").toString():null;
					
			//填充CRM要求的usertoken
			CRMCustmemberBasicInfo basicInfo = redisService.getUserInfoByTicket(ticket);
			
			if(null != basicInfo && StringUtil.isNotEmpty(basicInfo.getCmmemid())){
				String userToken = cRMInterfaceService.getValidUserTokenFromDB(basicInfo.getCmmemid());
				params.put("token", userToken);
				params.put("p_token", userToken);
				
				requestparam.setParams(params);
				//此处可做容错处理
				//如果CRM返回accessToken错误或者usertoken错误，可以处理后，重新再发送请求
				//
				//
				ResponseFromCRMData crmResponse =  cRMInterfaceService.sendCommonRequestToCRM(crmInterfaceCode, requestparam);
				logger.info("~~~"+methodName+"~~~JSONArray.toJSON(crmResponse):{}",null != crmResponse?JSONArray.toJSON(crmResponse):null);
				
				response.setCode(crmResponse.getCode());
				response.setMsg(crmResponse.getMsg());
				response.setData(crmResponse.getData());
				logger.info("~~~"+methodName+"~~~JSONArray.toJSON(response):{}", JSONArray.toJSON(response));
			}else{
				response.setCode("9");
				response.setMsg("ticket 不存在");
			}
		}catch(Exception e){
			response.setCode("1");
			response.setMsg("系统异常");
			logger.error("~~~"+methodName+"~~~exception~~",e);
		}
		logger.info("~~~"+methodName+"~~~end~~");
		return response;
	}
}
