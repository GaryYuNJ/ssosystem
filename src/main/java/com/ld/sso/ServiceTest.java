package com.ld.sso;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ld.sso.oa.service.IOAService;

@EnableAsync
@SpringBootTest(classes = SpringbootApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTest {

    @Autowired
    private IOAService oAService;

    @Test
    public void testOAchangeUserMobile() throws Exception {
    	//通知OA
		oAService.changeUserMobile("111111", "13851781324");
    }
}