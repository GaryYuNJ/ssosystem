package com.ld.sso.crm.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import tk.mybatis.mapper.util.StringUtil;

import com.alibaba.fastjson.JSONArray;
import com.ld.sso.crm.databean.ResponseFromCRMData;
import com.ld.sso.crm.domain.CRMCustmemberModel;
import com.ld.sso.crm.domain.CardJFLogModel;
import com.ld.sso.crm.domain.CusttypeModel;
import com.ld.sso.crm.mapper.CRMCustmemberModelMapper;
import com.ld.sso.crm.mapper.CardJFLogModelMapper;
import com.ld.sso.crm.properties.CRMInterfaceProperties;
import com.ld.sso.crm.service.ICRMInterfaceService;
import com.ld.sso.crm.util.CRMCharacterConverter;
import com.ld.sso.frontlayer.databean.CommonRequestParam;
import com.ld.sso.midlayer.service.IuserInfoService;

@Service
public class CRMInterfaceServiceImpl implements ICRMInterfaceService {

	Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private CRMCustmemberModelMapper custmemberMapper;
	@Autowired
	private CardJFLogModelMapper cardJFLogModelMapper;
	@Autowired 
	private CRMInterfaceProperties crmInterfaceProperties; 
	@Autowired 
	IuserInfoService userInfoService;
	
	@Override
	public CRMCustmemberModel authUserLogin(String mobile, String password) {
		logger.info("~~~authUserLogin()~~~start~~mobile:{}", mobile);
		//call oracle method get encrypt password
		Map params = new HashMap();
		params.put("mobile", mobile);
		params.put("enPasswd", "");
		params.put("passwd", password);
        
		custmemberMapper.getEncryptedPasswd(params);
		logger.info("~~~authUserLogin()~~~map.get(enPasswd):{}", params.get("enPasswd"));
		
//		map.put("passwd", "");
//		custmemberMapper.getDecryptedPasswd(map);
//		logger.info("~~~authUserLogin()~~~map.get(passwd):{}", map.get("passwd"));
		
		logger.info("~~~authUserLogin()~~~end~~mobile:{}", mobile);
//		//query use by mobile and encrypted password
		return custmemberMapper.selectBasicInfoByMobileAndEnPasswd(params);
	}
	
	
	/*
		从数据库获取最新的usertoken;
		如果有数据，未过期(7天为限)，直接返回；
		如果没有数据，直接创建；
		如果已过期(7天为限)，更新token生成时间为当天；
	*/
	@Override
	public String getValidUserTokenFromDB(String cmmemId) {
		logger.info("~~~getValidUserToken()~~~start~~cmmemId:{}", cmmemId);
		//获得6天之前的时间
		Date validDate=new Date((new Date()).getTime() - 6*24*3600000);
		
		CRMCustmemberModel cusModel = custmemberMapper.selectTokenInfoByPrimaryKey(cmmemId);
		if(null != cusModel){
			logger.info("~~~getValidUserToken()~~~cusModel is not null~~");
			if(null != cusModel.getCmtoken()){
				logger.info("~~~getValidUserToken()~~~current token is: {}~~", cusModel.getCmtoken());
				//不为空，并且未过期，直接返回
				if(null != cusModel.getCmtokendate()
						&& cusModel.getCmtokendate().after(validDate)){
					logger.info("~~~getValidUserToken()~~~current token is valid~~");
					return cusModel.getCmtoken();
					
				//tokenDate为空或者已过期，重新生成
				}else{
					logger.info("~~~getValidUserToken()~~~update token date~~");
					cusModel.setCmtokendate(new Date());
				}
			//重新生成token和date
			}else{
				logger.info("~~~getValidUserToken()~~~generate new token and date~~");
				//生成四位随机数
				int random = (int)(Math.random()*(9999-1000+1))+1000;
				cusModel.setCmtoken(cmmemId+random);
				cusModel.setCmtokendate(new Date());
			}
			logger.info("~~~getValidUserToken()~~~update token and date~~cusModel:{}",cusModel.getCmtokendate());
			//保存token和时间
			cusModel.setCmmemid(cmmemId);
			int flag = custmemberMapper.updateTokenByPrimaryKey(cusModel);
			logger.info("~~~getValidUserToken()~~~update token and date~~flag:{}",flag);
			if(flag > 0 ){
				return cusModel.getCmtoken();
			}
		}else{
			logger.info("~~~getValidUserToken()~~~cusModel is null~~");
		}
		logger.info("~~~getValidUserToken()~~~end~~cmmemId:{}", cmmemId);
		return null;
	}
	
	@Override
	public CRMCustmemberModel getFullInfoByPrimaryKey(String cmmemid) {
		
		CRMCustmemberModel cusModel = custmemberMapper.selectFullInfoByPrimaryKey(cmmemid);
		if(null == cusModel){
			return null;
		}
		
		if(null != cusModel.getCustypeModel()){
			CusttypeModel cusType = cusModel.getCustypeModel();
			cusType.setCtname(null != cusType.getCtname() ? CRMCharacterConverter.convert8859P1ToGBK(cusType.getCtname()):null);
			cusModel.setCustypeModel(cusType);
		}
		
		cusModel.setCmname(null != cusModel.getCmname() ? CRMCharacterConverter.convert8859P1ToGBK(cusModel.getCmname()):null);
		return cusModel;
	}
	
	@Override
	public BigDecimal getCurJFYEByCustId(String cmcustid) {
		CardJFLogModel jfModel = cardJFLogModelMapper.selectCurJFYEByCustId(cmcustid);
		return jfModel.getCdlcurjfye();
	}
	
	
	@Override
	public String generateNewAccessToken() {
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseFromCRMData response = restTemplate.getForObject(crmInterfaceProperties.getAccessTokenUrl(), 
				ResponseFromCRMData.class); 
		logger.info("~~~generateNewAccessToken()~~~response:{}", JSONArray.toJSON(response));

		return response.getAccess_token();
	}

	@Override
	public int modifyPassword(String cmmemid, String newPassword) {
		logger.info("~~~modifyPassword()~~~start~~cmmemId:{},newPassword:{}", cmmemid,newPassword);
		Map params = new HashMap();
		params.put("enPasswd", "");
		params.put("passwd", newPassword);
		custmemberMapper.getEncryptedPasswd(params);
		
		if(null != params.get("enPasswd") 
				&& StringUtil.isNotEmpty(params.get("enPasswd").toString())){
			
			CRMCustmemberModel cusModel = new CRMCustmemberModel();
			cusModel.setCmmemid(cmmemid);
			cusModel.setCmpwd(params.get("enPasswd").toString());
			
			return custmemberMapper.updateByPrimaryKeySelective(cusModel);
		}
		
		return 0;
	}

	@Override
	public int modifyPasswordByMobile(String mobile, String newPassword) {
		logger.info("~~~modifyPasswordByMobile()~~~start~~mobile:{},newPassword:{}", mobile,newPassword);
		Map params = new HashMap();
		params.put("enPasswd", "");
		params.put("passwd", newPassword);
		custmemberMapper.getEncryptedPasswd(params);
		
		if(null != params.get("enPasswd") 
				&& StringUtil.isNotEmpty(params.get("enPasswd").toString())){
			
			CRMCustmemberModel cusModel = new CRMCustmemberModel();
			cusModel.setCmmobile1(mobile);
			cusModel.setCmpwd(params.get("enPasswd").toString());
			
			return custmemberMapper.updateByMobileSelective(cusModel);
		}
		
		return 0;
	}
	
	
	@Override
	public int modifyUserInfoByPrimaryKey(CRMCustmemberModel cusModel) {
		
		cusModel.setCmname(null != cusModel.getCmname() ? CRMCharacterConverter.convertGBKTo8859P1(cusModel.getCmname()):null);
		
		return custmemberMapper.updateByPrimaryKeySelective(cusModel);
		
	}

	//调用CRM通用接口
	@Override
	public ResponseFromCRMData sendCommonRequestToCRM(String crmInterfaceCode,
			CommonRequestParam requestparam) {
		logger.info("~~~commonRequestToCRM()~~~start~~crmInterfaceCode:{},requestparam:{}", crmInterfaceCode, JSONArray.toJSON(requestparam));
		RestTemplate restTemplate = new RestTemplate();
		
		String requestUrl = crmInterfaceProperties.getFullServiceurl(crmInterfaceCode, userInfoService.getValidCRMAccessToken());
		logger.info("~~~commonRequestToCRM()~~~start~~requestUrl:{}", requestUrl);
		
		return restTemplate.postForObject(requestUrl, requestparam, ResponseFromCRMData.class); 
	}
	
}
