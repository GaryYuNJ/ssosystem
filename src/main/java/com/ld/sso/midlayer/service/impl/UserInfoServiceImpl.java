package com.ld.sso.midlayer.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.util.StringUtil;

import com.alibaba.fastjson.JSONArray;
import com.ld.sso.crm.databean.CRMAccessTokenInfo;
import com.ld.sso.crm.databean.ResponseFromCRMData;
import com.ld.sso.crm.domain.CRMCustmemberModel;
import com.ld.sso.crm.domain.CardJFLogModel;
import com.ld.sso.crm.properties.CRMInterfaceProperties;
import com.ld.sso.crm.service.ICRMInterfaceService;
import com.ld.sso.crm.util.CRMCharacterConverter;
import com.ld.sso.frontlayer.databean.CommonRequestParam;
import com.ld.sso.frontlayer.databean.CommonResponseInfo;
import com.ld.sso.midlayer.databean.CRMCustmemberBasicInfo;
import com.ld.sso.midlayer.databean.CRMCustmemberFullInfo;
import com.ld.sso.midlayer.databean.CardJFLogSimpleDataBean;
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
	
	@Autowired 
	private CRMInterfaceProperties crmInterfaceProperties; 

	@Override
	public CommonResponseInfo loginWithPwd(String mobile, String password, String source) {
		logger.info("~~~loginWithPwd()~~~start~~mobile:{},password:{},source:{}", mobile, password,source);
		CommonResponseInfo responseInfo = new CommonResponseInfo();
		try{
			
			CRMCustmemberModel cusModel = cRMInterfaceService.authUserLogin(mobile, password);
			
			if(null != cusModel && StringUtil.isNotEmpty(cusModel.getCmmemid())){
				
				//密码是手机号码后6位禁止登陆，要求用户走忘记密码流程修改密码
				//新密码不能是手机后六位
				if(password.equals(mobile.substring(5))){
					responseInfo.setCode("9905");
					responseInfo.setMsg("密码与手机号后六位相同，请点击忘记密码重置");
					
				}else{
					CRMCustmemberBasicInfo basicInfo = new CRMCustmemberBasicInfo();
					basicInfo.setCmcustid(cusModel.getCmcustid());
					basicInfo.setCmmemid(cusModel.getCmmemid());
					basicInfo.setCmmobile(cusModel.getCmmobile1());
					
					responseInfo.setData(basicInfo);
					responseInfo.setCode("0");
					responseInfo.setMsg("");
					responseInfo.setTicket(this.getNewSSOTicket(cusModel.getCmmemid(), source, basicInfo));
				}
			}else{
				responseInfo.setCode("9904");
				responseInfo.setMsg("用户不存在或者密码错误");
			}
			
		}catch(Exception e){
			responseInfo.setCode("9901");
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
	public CRMCustmemberBasicInfo queryUserBasicInfoForEvcard(String ticket) {
		final String methodName = "queryUserBasicInfoForEvcard()";
		logger.info("~~~"+methodName+"~~~start~~ticket:{}",ticket);
		try{
			
			return redisService.getUserInfoByTicket(ticket);
		}catch(Exception e){
			logger.error("~~~"+methodName+"~~~exception~~",e);
			return null;
		}
		// TODO Auto-generated method stub
	}
	
	@Override
	public CommonResponseInfo queryUserBasicInfoByTicket(String ticket) {
		final String methodName = "queryUserBasicInfoByTicket()";
		logger.info("~~~"+methodName+"~~~start~~ticket:{}",ticket);
		CommonResponseInfo response = new CommonResponseInfo();
		try{
			
			CRMCustmemberBasicInfo basicInfo = redisService.getUserInfoByTicket(ticket);
			if(null != basicInfo && StringUtil.isNotEmpty(basicInfo.getCmmemid())){
				response.setData(basicInfo);
				response.setCode("0");
			}else{
				response.setCode("9909");
				response.setMsg("ticket 不存在");
			}
		}catch(Exception e){
			response.setCode("9901");
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
				//替换真正的积分余额
				cusModel.setCmtotjf(cRMInterfaceService.getCurJFYEByCustId(basicInfo.getCmcustid()));
				//替换真正的成长值CUSTOMER。CNUM10，关联 CMCUSTID
				cusModel.setCmczz(cRMInterfaceService.getCmczzByCustId(basicInfo.getCmcustid()));
				
				if(null != cusModel && StringUtil.isNotEmpty(cusModel.getCmmemid())){
					custModelToFullInfoConverter.convert(cusModel, fullInfo);
					response.setCode("0");
					response.setData(fullInfo);
				}else{
					response.setCode("9903");
					response.setMsg("数据库获取用户信息失败");
					response.setData(basicInfo);
					logger.error("~~~"+methodName+"~~~get fullInfo failed~~");
				}
				
			}else{
				response.setCode("9909");
				response.setMsg("ticket 不存在");
			}
		}catch(Exception e){
			response.setCode("9901");
			response.setMsg("系统异常");
			logger.error("~~~"+methodName+"~~~exception~~",e);
		}
		logger.info("~~~"+methodName+"~~~end~~");
		// TODO Auto-generated method stub
		return response;
	}
	
	//查询用户积分余额
	@Override
	public CommonResponseInfo queryJFBalance(String ticket) {
		final String methodName = "queryJFBalance()";
		
		logger.info("~~~"+methodName+"~~~start~~ticket:{}",ticket);
		CommonResponseInfo response = new CommonResponseInfo();
		try{
			
			CRMCustmemberBasicInfo basicInfo = redisService.getUserInfoByTicket(ticket);
			
			if(null != basicInfo && StringUtil.isNotEmpty(basicInfo.getCmcustid())){
				
				BigDecimal jfBalance = cRMInterfaceService.getCurJFYEByCustId(basicInfo.getCmcustid());
				
				Map<String,Integer> map = new HashMap<String,Integer>();
				map.put("jfBalance", 0);
				if(null != jfBalance){
					map.put("jfBalance", jfBalance.intValue());
				}
				response.setCode("0");
				response.setData(map);
				
			}else{
				response.setCode("9909");
				response.setMsg("ticket 不存在");
			}
		}catch(Exception e){
			response.setCode("9901");
			response.setMsg("系统异常");
			logger.error("~~~"+methodName+"~~~exception~~",e);
		}
		logger.info("~~~"+methodName+"~~~end~~");
		// TODO Auto-generated method stub
		return response;
	}
	
	//根据查询用户积分余额
	@Override
	public CommonResponseInfo queryJFBalanceByCMcustid(String mobile, String cmcustid) {
		final String methodName = "queryJFBalance()";
		
		logger.info("~~~"+methodName+"~~~start~~cmcustid:{}",cmcustid);
		logger.info("~~~"+methodName+"~~~start~~mobile:{}",mobile);
		CommonResponseInfo response = new CommonResponseInfo();
		try{
			
			if(StringUtil.isNotEmpty(cmcustid)){
				
				BigDecimal jfBalance = cRMInterfaceService.getCurJFYEByCustId(cmcustid);
				
				Map<String,Integer> map = new HashMap<String,Integer>();
				map.put("jfBalance", 0);
				if(null != jfBalance){
					map.put("jfBalance", jfBalance.intValue());
				}
				response.setCode("0");
				response.setData(map);
				
			}else{
				response.setCode("9909");
				response.setMsg("ticket 不存在");
			}
		}catch(Exception e){
			response.setCode("9901");
			response.setMsg("系统异常");
			logger.error("~~~"+methodName+"~~~exception~~",e);
		}
		logger.info("~~~"+methodName+"~~~end~~");
		// TODO Auto-generated method stub
		return response;
	}

	//查询用户概要。包含积分余额，总历史消费积分，总历史获取积分
	@Override
	public CommonResponseInfo queryJFSummary(String ticket) {
		final String methodName = "queryJFBalance()";
		
		logger.info("~~~"+methodName+"~~~start~~ticket:{}",ticket);
		CommonResponseInfo response = new CommonResponseInfo();
		try{
			
			CRMCustmemberBasicInfo basicInfo = redisService.getUserInfoByTicket(ticket);
			
			if(null != basicInfo && StringUtil.isNotEmpty(basicInfo.getCmcustid())){
				
				BigDecimal jfBalance = cRMInterfaceService.getCurJFYEByCustId(basicInfo.getCmcustid());
				BigDecimal jfAddTotal = cRMInterfaceService.getJFAddTotalByCustId(basicInfo.getCmcustid());
				BigDecimal jfSubTotal = cRMInterfaceService.getJFSubTotalByCustId(basicInfo.getCmcustid());
				
				Map<String,Integer> map = new HashMap<String,Integer>();
				map.put("jfBalance", 0);
				map.put("jfAddTotal", 0);
				map.put("jfSubTotal", 0);
				if(null != jfBalance){
					map.put("jfBalance", jfBalance.intValue());
				}
				if(null != jfAddTotal){
					map.put("jfAddTotal", jfAddTotal.intValue());
				}
				if(null != jfSubTotal){
					map.put("jfSubTotal", jfSubTotal.intValue());
				}
				response.setCode("0");
				response.setData(map);
				
			}else{
				response.setCode("9909");
				response.setMsg("ticket 不存在");
			}
		}catch(Exception e){
			response.setCode("9901");
			response.setMsg("系统异常");
			logger.error("~~~"+methodName+"~~~exception~~",e);
		}
		logger.info("~~~"+methodName+"~~~end~~");
		// TODO Auto-generated method stub
		return response;
	}


	//查询历史获取积分列表
	@Override
	public CommonResponseInfo queryJFHistoryList(String ticket, int startRow, int pageSize, int type) {
		final String methodName = "queryJFBalance()";
		
		logger.info("~~~"+methodName+"~~~start~~ticket:{}",ticket);
		CommonResponseInfo response = new CommonResponseInfo();
		try{
			
			CRMCustmemberBasicInfo basicInfo = redisService.getUserInfoByTicket(ticket);
			
			if(null != basicInfo && StringUtil.isNotEmpty(basicInfo.getCmcustid())){
				List<CardJFLogModel> jfHistoryList = null;
				//查询消费历史记录
				if(type == -1){
					jfHistoryList = cRMInterfaceService.getJFSubListByCustId(basicInfo.getCmcustid(), startRow, pageSize);
				}else if(type == 1){
					jfHistoryList = cRMInterfaceService.getJFAddListByCustId(basicInfo.getCmcustid(), startRow, pageSize);
				}else{
					jfHistoryList = cRMInterfaceService.getJFHistoryListByCustId(basicInfo.getCmcustid(), startRow, pageSize);
				}
				
				CardJFLogSimpleDataBean cardJFLogSimpleDataBean = null;
				List<CardJFLogSimpleDataBean> cardJFLogSimpleList = new ArrayList<CardJFLogSimpleDataBean>();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				for(CardJFLogModel cardJFLogModel : jfHistoryList){
					cardJFLogSimpleDataBean = new CardJFLogSimpleDataBean();
					cardJFLogSimpleDataBean.setCdlcurjffs(cardJFLogModel.getCdlcurjffs());
					cardJFLogSimpleDataBean.setCdlcurjffsStr(cardJFLogModel.getCdlcurjffs().intValue()>0?("+"+cardJFLogModel.getCdlcurjffs().intValue()):(""+cardJFLogModel.getCdlcurjffs().intValue()));
					cardJFLogSimpleDataBean.setCdlcurjfye(cardJFLogModel.getCdlcurjfye());
					cardJFLogSimpleDataBean.setCdldate(cardJFLogModel.getCdldate());
					
					cardJFLogSimpleDataBean.setCdldateStr(format.format(cardJFLogModel.getCdldate()));
					cardJFLogSimpleDataBean.setCdlmemo(null != cardJFLogModel.getCdlmemo() ? CRMCharacterConverter.convert8859P1ToGBK(cardJFLogModel.getCdlmemo()):null);
					cardJFLogSimpleDataBean.setCdlmkt(cardJFLogModel.getCdlmkt());
					
					cardJFLogSimpleList.add(cardJFLogSimpleDataBean);
				}
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("jfHistoryList", cardJFLogSimpleList);
				response.setCode("0");
				response.setData(map);
				
			}else{
				response.setCode("9909");
				response.setMsg("ticket 不存在");
			}
		}catch(Exception e){
			response.setCode("9901");
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
			response.setCode("9901");
			response.setMsg("系统异常");
			logger.error("~~~"+methodName+"~~~exception~~",e);
		}
		logger.info("~~~"+methodName+"~~~end~~");
		return response;
	}

	@Override
	public CommonResponseInfo setNewPassword(String mobile, String newPassword) {
		final String methodName = "setNewPassword()";
		
		logger.info("~~~"+methodName+"~~~start~~mobile:{}",mobile);
		CommonResponseInfo response = new CommonResponseInfo();
		try{
			//新密码不能是手机后六位
			if(newPassword.equals(mobile.substring(5))){
				response.setCode("9905");
				response.setMsg("密码不能是手机号后六位");
				
			}else if(cRMInterfaceService.modifyPasswordByMobile(mobile, newPassword) > 0 ){
				response.setCode("0");
			}else{
				response.setCode("9903");
				response.setMsg("该手机号未注册");
			}
					
		}catch(Exception e){
			response.setCode("9901");
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
					//新密码不能是手机后六位
					if(newPassword.equals(basicInfo.getCmmobile().substring(5))){
						response.setCode("9905");
						response.setMsg("密码不能是手机号后六位");
						
					}else if(cRMInterfaceService.modifyPassword(basicInfo.getCmmemid(), newPassword) > 0 ){
						response.setCode("0");
						
					}else{
						response.setCode("9903");
						response.setMsg("密码修改失败");
					}
					
				}else{
					response.setCode("9902");
					response.setMsg("原始密码不对");
				}
			}else{
				response.setCode("9909");
				response.setMsg("ticket 不存在");
			}
		}catch(Exception e){
			response.setCode("9901");
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
					response.setCode("9903");
					response.setMsg("修改失败");
				}
			}else{
				response.setCode("9909");
				response.setMsg("ticket 不存在");
			}
		}catch(Exception e){
			response.setCode("9901");
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
		
		//Access_token默认7天有效期，为了系统可用性，这里只要超过2天就重新获取
		//access_token供整个SSO平台使用，频繁获取新token对整个用户群没影响
		//CRM限制一个sno每天获取Access_token次数不能超过2000次
		Date validDate=new Date((new Date()).getTime() - 2*24*3600000);
				
		CRMAccessTokenInfo accessTokenInfo = redisService.getCRMAccessToken();
		//判断是否过期
		if(null != accessTokenInfo 
				&& null != accessTokenInfo.getCreateDate()
				&& accessTokenInfo.getCreateDate().after(validDate)
				&& !StringUtil.isEmpty(accessTokenInfo.getAccessToken())){
			accessToken = accessTokenInfo.getAccessToken();
		}else{
			accessToken = cRMInterfaceService.generateNewAccessToken();
			//允许一次容错，换一个sno
			if(StringUtil.isEmpty(accessToken) ){
				logger.warn("~~~"+methodName+"~~get accessToken again");
				accessToken = cRMInterfaceService.generateNewAccessToken();
			}

			//不为null,保存到cache
			if( !StringUtil.isEmpty(accessToken)){
				accessTokenInfo = new CRMAccessTokenInfo();
				accessTokenInfo.setAccessToken(accessToken);
				accessTokenInfo.setCreateDate(new Date());
				redisService.saveCRMAccessToken(accessTokenInfo);
			}
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
		
		//如果是注册时，不允许密码是手机后六位
		try{
			if(crmInterfaceProperties.getRegisterCode().equals(crmInterfaceCode)){
				if((requestparam.getParams().get(("p_mobile").substring(5))).equals(requestparam.getParams().get(("p_pwd")))){
					response.setCode("9905");
					response.setMsg("密码不能是手机号后六位");
					return response;
				}
			}
		}catch(Exception e){
			logger.error("", e);
		}
		
		try{
			//解析请求入参
			Map<String, Object> params = requestparam.getParams();
			//获取ticket
			String ticket = (null != params && null != params.get("ticket")) ?
					requestparam.getParams().get("ticket").toString():null;
			
			//ticket为空时不需要usertoken
			//填充CRM要求的usertoken，有些接口不需要usertoken，只要直接转发就可以。比如 注册接口
			CRMCustmemberBasicInfo basicInfo = null;
			String userToken = null;
			
			//如果不需要usertoken，那么也不需要ticket
			if(!crmInterfaceProperties.getUserTokenNoNeedCodes().contains(crmInterfaceCode)){
				
				if(StringUtil.isNotEmpty(ticket)){
					basicInfo = redisService.getUserInfoByTicket(ticket);
				}
				
				if(null != basicInfo && StringUtil.isNotEmpty(basicInfo.getCmmemid())){
					
//					//从缓存中读取用户对应的userToken，不保证在CRM有没有过期
//					userToken = redisService.getCRMUserToken(basicInfo.getCmmemid());
//					
//					//如果user token不存在缓存，获取可用的usertoken并存入缓存
//					if(StringUtil.isEmpty(userToken)){
//						userToken = cRMInterfaceService.getValidUserTokenFromDB(basicInfo.getCmmemid());
//						redisService.saveCRMUserToken(basicInfo.getCmmemid(), userToken);
//					}
					//为防止意外，直接俄从数据库获取可用token
					userToken = cRMInterfaceService.getValidUserTokenFromDB(basicInfo.getCmmemid());
					
					params.put("token", userToken);
					params.put("p_token", userToken);
					
				}else{
					response.setCode("9909");
					response.setMsg("ticket已过期或者不存在");
					return response;
				}
			}
			
			//发送请求
			requestparam.setParams(params);
			ResponseFromCRMData crmResponse =  cRMInterfaceService.sendCommonRequestToCRM(crmInterfaceCode, requestparam);
			logger.info("~~~"+methodName+"~~~JSONArray.toJSON(crmResponse):{}",null != crmResponse?JSONArray.toJSON(crmResponse):null);
					
			//usertoken过期容错处理(上面直接从数据库中获取，这里不再做容错处理)
			//code = 0 ,data 为空 表示 user token 过期????
			//if(!crmInterfaceProperties.getUserTokenNoNeedCodes().contains(crmInterfaceCode)
//					&& "0".equals(crmResponse.getCode())
//					&& (null == crmResponse.getData() ||crmResponse.getData().size() == 0)){
//				//获取可用的usertoken并存入缓存
//				logger.info("~~~"+methodName+"~~~usertoken expired. get the new one from DB~~");
//				userToken = cRMInterfaceService.getValidUserTokenFromDB(basicInfo.getCmmemid());
//				logger.info("~~~"+methodName+"~~~new userToken:{}~~", userToken);
//				redisService.saveCRMUserToken(basicInfo.getCmmemid(), userToken);
//				
//				//重新发送请求
//				params.put("token", userToken);
//				params.put("p_token", userToken);
//				requestparam.setParams(params);
//				crmResponse =  cRMInterfaceService.sendCommonRequestToCRM(crmInterfaceCode, requestparam);
//				logger.info("~~~"+methodName+"~~~JSONArray.toJSON(crmResponse):{}",null != crmResponse?JSONArray.toJSON(crmResponse):null);
			
			//access token容错处理
			//如果CRM返回accessToken 错误，可以重新获取后，再发送请求
			//}else 
				if("9003".equals(crmResponse.getCode())//access token 无效
					//access token已过期
					|| "9005".equals(crmResponse.getCode())){
					logger.warn("~~~"+methodName+"~~~access token invalid~~crm~return code:{}",crmResponse.getCode());
					logger.warn("~~~"+methodName+"~~~get new access code and resend request to crm~~ ");
				//直接删除缓存的accesstoken
				redisService.deleteCRMAccessToken();
				
				crmResponse =  cRMInterfaceService.sendCommonRequestToCRM(crmInterfaceCode, requestparam);
				logger.info("~~~"+methodName+"~~~JSONArray.toJSON(crmResponse):{}",null != crmResponse?JSONArray.toJSON(crmResponse):null);
			}
					
			response.setCode(crmResponse.getCode());
			response.setMsg(crmResponse.getMsg());
			response.setData(crmResponse.getData());
			logger.info("~~~"+methodName+"~~~JSONArray.toJSON(response):{}", JSONArray.toJSON(response));
			
		}catch(Exception e){
			response.setCode("9901");
			response.setMsg("系统异常");
			logger.error("~~~"+methodName+"~~~exception~~",e);
		}
		logger.info("~~~"+methodName+"~~~end~~");
		return response;
	}

	@Override
	public CommonResponseInfo changeCusJFByMemId(String crmInterfaceCode,
			CommonRequestParam requestparam) {
		final String methodName = "changeCusJFByMemId()";
		logger.info("~~~"+methodName+"~~~start~~");
		logger.warn("~~~"+methodName+"~~~JSONArray.toJSON(requestparam):{}", JSONArray.toJSON(requestparam));
		
		Map<String, Object> paramTmp = requestparam.getParams();
		
		CommonResponseInfo response =  new CommonResponseInfo();
		String userToken = cRMInterfaceService.getValidUserTokenFromDB(requestparam.getParams().get("cmmemberId").toString());
		
		paramTmp.put("token", userToken);
		paramTmp.put("p_token", userToken);
		//发送请求
		requestparam.setParams(paramTmp);
		ResponseFromCRMData crmResponse =  cRMInterfaceService.sendCommonRequestToCRM(crmInterfaceCode, requestparam);
		logger.info("~~~changeCusJFByMemId()~~~JSONArray.toJSON(crmResponse):{}",null != crmResponse?JSONArray.toJSON(crmResponse):null);
		
		response.setCode(crmResponse.getCode());
		response.setMsg(crmResponse.getMsg());
		response.setData(crmResponse.getData());
		logger.info("~~~changeCusJFByMemId()~~~JSONArray.toJSON(response):{}", JSONArray.toJSON(response));
		return response;
		
	}
}
