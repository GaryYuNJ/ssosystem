package com.ld.sso.redis.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ld.sso.crm.databean.CRMAccessTokenInfo;
import com.ld.sso.midlayer.databean.CRMCustmemberBasicInfo;
import com.ld.sso.redis.service.IRedisService;


/**
 */
@CacheConfig(cacheNames="userbasicinfo")
@Service
public class RedisService implements IRedisService {

	Logger logger = LogManager.getLogger(this.getClass());
	
//    @Autowired
//    StringRedisTemplate stringRedisTemplate;
//
//    @Override
//    public void textfun() {
//
//        SetOperations set = stringRedisTemplate.opsForSet();
//        set.add("openid", "oevMOt4FQdG5wxAuKUEWQ7H1C-Y");
//        
//        ValueOperations value = stringRedisTemplate.opsForValue();
//        value.set("openid1", "oevMOt4FQdG5wxAuKUEWQ7H1C-Y");
//        
//        logger.info("!CollectionUtils.isEmpty(set.members( \"openid\")) = " + !CollectionUtils.isEmpty(set.members("openid")));
//        logger.info("set.members(\"openid\") = " + set.members("openid"));
//        try {
//            Thread.sleep(1500);
//            logger.info("!CollectionUtils.isEmpty(set.members(\"openid\")) = " + !CollectionUtils.isEmpty(set.members("openid")));
//            stringRedisTemplate.expire("openid", 60, TimeUnit.SECONDS);
//            Thread.sleep(1500);
//            logger.info("!CollectionUtils.isEmpty(set.members(\"openid\"))) = " + !CollectionUtils.isEmpty(set.members("openid")));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
    
	//当key已存在时，只读取缓存；不存在就走方法取值并保存
	//@Cacheable(value="test1122", key="#id+'test1'")    
	@Override
	@Cacheable(value="userbasicinfo", key="#ticket")
	public CRMCustmemberBasicInfo getUserInfoByTicket(String ticket) {
		logger.info("--getUserInfoByTicket()---ticket: {} --- related userinfo in redis is null", ticket);
		//无缓存的时候调用这里
		return null;
	}
		
	//	不管存不存在都会更新key对于的内容
	@Override
	@CachePut (value="userbasicinfo", key="#ticket")    
	public CRMCustmemberBasicInfo saveUserInfoInCache(String ticket, CRMCustmemberBasicInfo userinfo) {
		logger.info("--saveUserInfo()--ticket: {}, userinfo.getCmmemid(): {}", ticket, userinfo.getCmmemid());
		return userinfo;
	}
		
	//删除key对应的内容
	@Override
	@CacheEvict(value="userbasicinfo", key="#ticket")    
	public void deleteUserInfoInCacheByTicket(String ticket) {
		logger.info("--deleteUserInfoInCacheByTicket（）--ticket: {}", ticket);
	}

	//不管存不存在都会更新key对于的内容
	@Override
	@CachePut (value="CRMAccessToken", key="'accessToken'")   
	public CRMAccessTokenInfo saveCRMAccessToken(CRMAccessTokenInfo accessTokenInfo) {
		logger.info("--saveCRMAccessToken（）--accessTokenInfo.getAccessToken(): {}", accessTokenInfo.getAccessToken());
		return accessTokenInfo;
	}

	//获取缓存中的accessToken，不存在就走方法取值并保存
	@Override
	@Cacheable(value="CRMAccessToken", key="'accessToken'")  
	public CRMAccessTokenInfo getCRMAccessToken() {
		//如果缓存不存在走这里，
		return null;
	}
//
//	@Override
//	@CachePut(value="CRMUserToken", key="#cmmemId")  
//	public String saveCRMUserToken(String cmmemId, String userToken) {
//		logger.info("--saveCRMUserToken()--cmmemId: {} --userToken: {}", cmmemId, userToken);
//		return userToken;
//	}
//
//	@Override
//	@Cacheable(value="CRMUserToken", key="#cmmemId")  
//	public String getCRMUserToken(String cmmemId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	@CachePut(value="memIdToTicketCache", key="#cmmemId_source")  
	public String saveMemIdToTicketCache(String cmmemId_source, String ticket) {
		logger.info("--saveMemIdTicketCache()--cmmemId_source: {} --ticket: {}", cmmemId_source, ticket);
		return ticket;
	}

	@Override
	@Cacheable(value="memIdToTicketCache", key="#cmmemId_source")  
	public String getMemIdToTicketCache(String cmmemId_source) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@CacheEvict(value="memIdToTicketCache", key="#cmmemId_source")  
	public String deleteMemIdToTicketCache(String cmmemId_source) {
		// TODO Auto-generated method stub
		return null;
	}

}
