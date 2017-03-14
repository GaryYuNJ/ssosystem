package com.ld.sso.redis.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ld.sso.crm.domain.CRMCustmemberModel;
import com.ld.sso.redis.service.IRedisService;

import java.util.concurrent.TimeUnit;

/**
 */
@CacheConfig(cacheNames="userinfo")
@Service
public class RedisService implements IRedisService {

	Logger logger = LogManager.getLogger(this.getClass());
	
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public void textfun() {

        SetOperations set = stringRedisTemplate.opsForSet();
        set.add("openid", "oevMOt4FQdG5wxAuKUEWQ7H1C-Y");
        
        ValueOperations value = stringRedisTemplate.opsForValue();
        value.set("openid1", "oevMOt4FQdG5wxAuKUEWQ7H1C-Y");
        
        logger.info("!CollectionUtils.isEmpty(set.members( \"openid\")) = " + !CollectionUtils.isEmpty(set.members("openid")));
        logger.info("set.members(\"openid\") = " + set.members("openid"));
        try {
            Thread.sleep(1500);
            logger.info("!CollectionUtils.isEmpty(set.members(\"openid\")) = " + !CollectionUtils.isEmpty(set.members("openid")));
            stringRedisTemplate.expire("openid", 60, TimeUnit.SECONDS);
            Thread.sleep(1500);
            logger.info("!CollectionUtils.isEmpty(set.members(\"openid\"))) = " + !CollectionUtils.isEmpty(set.members("openid")));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
	//当key已存在时不保存，只读取
	//@Cacheable(value="test1122", key="#id+'test1'")    
	@Cacheable(key="#id+'test1'")
	public CRMCustmemberModel searchUserInfo(int id) {
		logger.info("id: {}, city: {}", id);
		return null;
	}
		
	//不管存不存在都会更新key对于的内容
//	@CachePut (key="#id+'test1'")    
//	public CityInfo saveCity(int id, String city) {
//		logger.info("id: {}, city: {}", id, city);
//		return new CityInfo(id, city);
//	}
		
	//删除key对应的内容
	@CacheEvict(key="#id+'test1'")    
	public void deleteCity(int id) {
		logger.info("id: {}, city: {}", id);
	}
	
}
