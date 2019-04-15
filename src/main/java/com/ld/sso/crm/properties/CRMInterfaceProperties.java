package com.ld.sso.crm.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
/*
#CRM interface code
#8001 会员登陆验证
loginCode=8001
#8002 会员密码修改
modifyPasswdCode=8002
#8013 会员信息变更
modifyUserinfoCode=8013
#8014 会员信息单个查询
queryuserinfoCode=8014
#8006 积分增减
changeJFCode=8006
#8009 会员积分记录查询接口
queryJFrecordCode=8009
#8012 会员注册
registerCode=8012
#8015 个人房产信息查询
queryHouseCode=8015
#8020 储值账户余额查询
queryBalanceCode=8020
#8021 储值账户消费明细查询
queryBalancedetailCode=8021
#8023 储值账户消费
consumeBalanceCode=8023
#8025 储值账户记录
queryBalanceRecordCode=8025
#80091 会员积分概况查询接口
queryJFSummaryCode=80091
#80092 根据类型（增加、扣减、所有）查询会员积分历史列表查询
queryJFHistoryCode=80092
#80093 发送短信验证码，返回messageId
sendVerifyCode=80093
#80094 验证短信码，返回成功、失败
verifyCode=80094
#80095 迁移手机号
migrateMobile=80095
#80095 验证手机号是否已注册
checkMobileRegStatus=80096
*/

@ConfigurationProperties(prefix = "crm.interface")
public class CRMInterfaceProperties {
	
	private String serviceUrl;
	private String accessTokenUrl;
	
	private String loginCode;
	private String modifyPasswdCode;
	private String modifyUserinfoCode;
	private String queryuserinfoCode;
	private String changeJFCode;
	private String queryJFrecordCode;
	private String registerCode;
	private String queryHouseCode;
	private String queryBalanceCode;
	private String queryBalancedetailCode;
	private String consumeBalanceCode;
	private String queryBalanceRecordCode;
	private String queryuserBasicinfoCode;
	private String signKey;
	private String logoutCode;
	private String queryJFBalanceCode;
	private String userTokenNoNeedCodes;
	private String newPasswordCode;
	private String queryJFSummaryCode;
	private String queryJFHistoryCode;
	private String sendVerifyCode;
	private String verifyCode;
	private String migrateMobile;
	private String checkMobileRegStatus;
	
	
	public String getCheckMobileRegStatus() {
		return checkMobileRegStatus;
	}
	public void setCheckMobileRegStatus(String checkMobileRegStatus) {
		this.checkMobileRegStatus = checkMobileRegStatus;
	}
	public String getMigrateMobile() {
		return migrateMobile;
	}
	public void setMigrateMobile(String migrateMobile) {
		this.migrateMobile = migrateMobile;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public String getSendVerifyCode() {
		return sendVerifyCode;
	}
	public void setSendVerifyCode(String sendVerifyCode) {
		this.sendVerifyCode = sendVerifyCode;
	}
	public String getQueryJFHistoryCode() {
		return queryJFHistoryCode;
	}
	public void setQueryJFHistoryCode(String queryJFHistoryCode) {
		this.queryJFHistoryCode = queryJFHistoryCode;
	}
	public String getQueryJFSummaryCode() {
		return queryJFSummaryCode;
	}
	public void setQueryJFSummaryCode(String queryJFSummaryCode) {
		this.queryJFSummaryCode = queryJFSummaryCode;
	}
	public String getNewPasswordCode() {
		return newPasswordCode;
	}
	public void setNewPasswordCode(String newPasswordCode) {
		this.newPasswordCode = newPasswordCode;
	}
	public String getQueryJFBalanceCode() {
		return queryJFBalanceCode;
	}
	public void setQueryJFBalanceCode(String queryJFBalanceCode) {
		this.queryJFBalanceCode = queryJFBalanceCode;
	}
	public String getUserTokenNoNeedCodes() {
		return userTokenNoNeedCodes;
	}
	public void setUserTokenNoNeedCodes(String userTokenNoNeedCodes) {
		this.userTokenNoNeedCodes = userTokenNoNeedCodes;
	}
	public String getLogoutCode() {
		return logoutCode;
	}
	public void setLogoutCode(String logoutCode) {
		this.logoutCode = logoutCode;
	}
	public String getSignKey() {
		return signKey;
	}
	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}
	public String getQueryuserBasicinfoCode() {
		return queryuserBasicinfoCode;
	}
	public void setQueryuserBasicinfoCode(String queryuserBasicinfoCode) {
		this.queryuserBasicinfoCode = queryuserBasicinfoCode;
	}
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	
	public String getAccessTokenUrl() {
		return accessTokenUrl;
	}
	public void setAccessTokenUrl(String accessTokenUrl) {
		this.accessTokenUrl = accessTokenUrl;
	}
	public String getLoginCode() {
		return loginCode;
	}
	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}
	public String getModifyPasswdCode() {
		return modifyPasswdCode;
	}
	public void setModifyPasswdCode(String modifyPasswdCode) {
		this.modifyPasswdCode = modifyPasswdCode;
	}
	public String getModifyUserinfoCode() {
		return modifyUserinfoCode;
	}
	public void setModifyUserinfoCode(String modifyUserinfoCode) {
		this.modifyUserinfoCode = modifyUserinfoCode;
	}
	public String getQueryuserinfoCode() {
		return queryuserinfoCode;
	}
	public void setQueryuserinfoCode(String queryuserinfoCode) {
		this.queryuserinfoCode = queryuserinfoCode;
	}
	public String getChangeJFCode() {
		return changeJFCode;
	}
	public void setChangeJFCode(String changeJFCode) {
		this.changeJFCode = changeJFCode;
	}
	public String getQueryJFrecordCode() {
		return queryJFrecordCode;
	}
	public void setQueryJFrecordCode(String queryJFrecordCode) {
		this.queryJFrecordCode = queryJFrecordCode;
	}
	public String getRegisterCode() {
		return registerCode;
	}
	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}
	public String getQueryHouseCode() {
		return queryHouseCode;
	}
	public void setQueryHouseCode(String queryHouseCode) {
		this.queryHouseCode = queryHouseCode;
	}
	public String getQueryBalanceCode() {
		return queryBalanceCode;
	}
	public void setQueryBalanceCode(String queryBalanceCode) {
		this.queryBalanceCode = queryBalanceCode;
	}
	public String getQueryBalancedetailCode() {
		return queryBalancedetailCode;
	}
	public void setQueryBalancedetailCode(String queryBalancedetailCode) {
		this.queryBalancedetailCode = queryBalancedetailCode;
	}
	public String getConsumeBalanceCode() {
		return consumeBalanceCode;
	}
	public void setConsumeBalanceCode(String consumeBalanceCode) {
		this.consumeBalanceCode = consumeBalanceCode;
	}
	public String getQueryBalanceRecordCode() {
		return queryBalanceRecordCode;
	}
	public void setQueryBalanceRecordCode(String queryBalanceRecordCode) {
		this.queryBalanceRecordCode = queryBalanceRecordCode;
	}

	public String getFullServiceurl(String interfaceCode, String accessToken){
		return this.getServiceUrl().replace("*interfaceCode*", interfaceCode).replace("*accessToken*", accessToken);
	}
	
	
}
