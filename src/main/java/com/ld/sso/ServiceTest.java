package com.ld.sso;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ld.sso.crm.mapper.CRMCustmemberModelMapper;
import com.ld.sso.oa.service.IOAService;

@EnableAsync
@SpringBootTest(classes = SpringbootApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTest {

    @Autowired
    private IOAService oAService;

    @Autowired
	private CRMCustmemberModelMapper custmemberMapper;
    
//    @Test
//    public void testOAchangeUserMobile() throws Exception {
//    	//通知OA
//		oAService.changeUserMobile("111111", "13851781324");
//    }
//    
    @Test
    public void getPassword() throws Exception {
    	Map params = new HashMap();
		params.put("mobile", "18752225902");
		params.put("enPasswd", "273163287204A729");
		params.put("passwd", "");
        
		custmemberMapper.getDecryptedPasswd(params);
		System.out.println("~~~~~~~~"+params.get("passwd"));
    }
}
