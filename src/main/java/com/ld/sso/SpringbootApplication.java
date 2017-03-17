package com.ld.sso;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.ld.sso.crm.mapper.CRMCustmemberModelMapper;
import com.ld.sso.crm.properties.CRMInterfaceProperties;
import com.ld.sso.crm.service.ICRMInterfaceService;
import com.ld.sso.frontlayer.databean.CommonRequestParam;
import com.ld.sso.frontlayer.databean.CommonResponseInfo;
import com.ld.sso.midlayer.service.IuserInfoService;
import com.ld.sso.redis.service.IRedisService;


@SpringBootApplication
@RestController
@MapperScan(basePackages = "com.ld.sso.crm.mapper")
@EnableCaching
@EnableScheduling
@EnableConfigurationProperties(CRMInterfaceProperties.class)
public class SpringbootApplication {
	
	@Autowired
	private CRMCustmemberModelMapper custmemberMapper;
	
	@Resource
    private IRedisService redisService;
	
	@Resource
	private ICRMInterfaceService cRMInterfaceService;
	
	@Resource
	private IuserInfoService userInfoService;
	
	@Autowired 
	private CRMInterfaceProperties crmInterfaceProperties; 
	
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    	//System.out.println(getSha1("1489390750794pamtest"));
       
    }

    @RequestMapping(value = "/")
    String hello() {
    	
    	// 测试redis
        //redisService.textfun();
//    	 System.out.println(crmInterfaceProperties.getAccessTokenUrl());
//    	 System.out.println(crmInterfaceProperties.getServiceUrl());
//    	 System.out.println(crmInterfaceProperties.getRegisterCode());
//    	 
//    	 System.out.println("mmmemid: "+cRMInterfaceService.authUserLogin("18705172915","111111").getCmmemid());
    	 CommonResponseInfo responseInfo = userInfoService.loginWithPwd("18705172915","111111", "APP");
    	 System.out.println("========"+JSONArray.toJSON(responseInfo)+"========");
    	 
    	 CommonRequestParam requestParam = new CommonRequestParam();
    	 requestParam.setSign("ac9e29882aecdf941bc9221312544ef2d0b5aa21");
    	 requestParam.setTimestamp("1489390123711");
    	 Map<String, Object> params = new HashMap<String, Object>();
    	 params.put("ticket", responseInfo.getTicket());
    	 params.put("num", 1);
    	 requestParam.setParams(params);
    	 
    	 CommonResponseInfo response = userInfoService.sendCommonRequestToCRM("8020", requestParam);
    	 System.out.println("========"+JSONArray.toJSON(response)+"========");
    	 //System.out.println("UserToken: "+cRMInterfaceService.getValidUserToken("0000000228"));
    	 
    	//System.out.println(this.custmemberMapper.selectByPrimaryKey("0000000228"));
        return "Hello World!";
    }
    
  
    
}