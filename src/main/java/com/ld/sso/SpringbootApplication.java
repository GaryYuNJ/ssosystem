package com.ld.sso;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ld.sso.crm.mapper.CRMCustmemberModelMapper;
import com.ld.sso.crm.mapper.CardJFLogModelMapper;
import com.ld.sso.crm.properties.CRMInterfaceProperties;
import com.ld.sso.crm.service.ICRMInterfaceService;
import com.ld.sso.midlayer.service.IuserInfoService;
import com.ld.sso.redis.service.IRedisService;


//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) //多数据源
@SpringBootApplication
@RestController
//@MapperScan(basePackages = "com.ld.sso.crm.mapper")
@EnableCaching
@EnableScheduling
@EnableAsync //开启异步操作,调用被@Async标记的方法的调用者不能和被调用的方法在同一类中不然不会起作用
@EnableConfigurationProperties(CRMInterfaceProperties.class)
public class SpringbootApplication {
	
	@Autowired
	private CRMCustmemberModelMapper custmemberMapper;
	
	@Autowired
	private CardJFLogModelMapper cardJFLogModelMapper;
	
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
////    	 
////    	 System.out.println("mmmemid: "+cRMInterfaceService.authUserLogin("18705172915","111111").getCmmemid());
//    	 CommonResponseInfo responseInfo = userInfoService.loginWithPwd("18705172915","111111", "APP");
//    	 System.out.println("========"+JSONArray.toJSON(responseInfo)+"========");
//    	 
//    	 CommonRequestParam requestParam = new CommonRequestParam();
//    	 requestParam.setSign("ac9e29882aecdf941bc9221312544ef2d0b5aa21");
//    	 requestParam.setTimestamp("1489390123711");
//    	 Map<String, Object> params = new HashMap<String, Object>();
//    	 params.put("ticket", responseInfo.getTicket());
//    	 params.put("num", 1);
//    	 requestParam.setParams(params);
//    	 
//    	 CommonResponseInfo response = userInfoService.sendCommonRequestToCRM("8020", requestParam);
//    	 System.out.println("========"+JSONArray.toJSON(response)+"========");
//    	 //System.out.println("UserToken: "+cRMInterfaceService.getValidUserToken("0000000228"));
//    	 
    	//System.out.println(this.custmemberMapper.selectByPrimaryKey("0000000228"));
    	Map params = new HashMap();
		params.put("mobile", "19951956360");
		params.put("enPasswd", "C36CF7C3A8562393");
		params.put("passwd", "");
        
		custmemberMapper.getDecryptedPasswd(params);
		System.out.println("~~~~~~~~"+params.get("passwd"));
		
//		String [] mobiles ={
//				
//				"13585386909",
//				"13775970289"
//		};
//		
//		for(int i=0;i<mobiles.length;i++){
//			
//			System.out.println(mobiles[i]+"	"
//			+  (cardJFLogModelMapper.selectCurJFYEByMobile(mobiles[i]) == null? 0:cardJFLogModelMapper.selectCurJFYEByMobile(mobiles[i]).getCdlcurjfye()));
//			
//		}
		
        return "Hello World!"+params.get("passwd");
    }
    
  
    
}