package com.ld.sso.crm.service.impl;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.ld.sso.crm.databean.ResponseFromCRMDataBean;
import com.ld.sso.crm.domain.CRMCustmemberModel;
import com.ld.sso.crm.mapper.CRMCustmemberModelMapper;
import com.ld.sso.crm.properties.CRMInterfaceProperties;
import com.ld.sso.crm.service.ICRMInterfaceService;

@Service
public class CRMInterfaceServiceImpl implements ICRMInterfaceService {

	Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private CRMCustmemberModelMapper custmemberMapper;
	@Autowired 
	private CRMInterfaceProperties crmInterfaceProperties; 
	
	/*
		从数据库获取最新的usertoken;
		如果有数据，未过期(7天为限)，直接返回；
		如果没有数据，直接创建；
		如果已过期(7天为限)，更新token生成时间为当天；
	*/
	@Override
	public String getValidUserToken(String cmmemId) {
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
					
				//为空或者已过期，重新生成tokenDate
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
	public String generateNewAccessToken() {
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseFromCRMDataBean response = restTemplate.getForObject(crmInterfaceProperties.getAccessTokenUrl(), 
				ResponseFromCRMDataBean.class); 
		logger.info("~~~generateNewAccessToken()~~~response:{}", JSONArray.toJSON(response));

		return response.getAccess_token();
	}
	
}
