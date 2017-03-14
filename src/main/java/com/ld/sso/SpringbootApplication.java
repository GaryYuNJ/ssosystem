package com.ld.sso;

import java.security.MessageDigest;

import javax.annotation.Resource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ld.sso.crm.mapper.CRMCustmemberModelMapper;
import com.ld.sso.redis.service.IRedisService;


@SpringBootApplication
@RestController
@MapperScan(basePackages = "com.ld.sso.crm.mapper")
@EnableCaching
@EnableScheduling
public class SpringbootApplication {
	
	@Autowired
	private CRMCustmemberModelMapper custmemberMapper;
	
	@Resource
    private IRedisService redisService;
	
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    	//System.out.println(getSha1("1489390750794pamtest"));
    }

    @RequestMapping(value = "/")
    String hello() {
    	
    	// 测试redis
        redisService.textfun();
        
    	System.out.println(this.custmemberMapper.selectByPrimaryKey("0000000230"));
        return "Hello World!";
    }
    
    
    public static String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];      
            }
            return new String(buf);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
    
}