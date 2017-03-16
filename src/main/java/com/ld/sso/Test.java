package com.ld.sso;

import com.alibaba.fastjson.JSONArray;
import com.ld.sso.crm.databean.ResponseFromCRMData;

public class Test {

	public static void main(String[] s){
		
		String response ="[{\"appId\":\"test\",\"code\":\"0\",\"msg\":\"\",\"timestamp\":\"1489656357518\",\"data\":[{\"cmlczhye\":\"0\"}],\"sign\":\"d4a73464fc114c936e6dc0330267b9ccb9b0b0d5\"}]";
		
		JSONArray.parseArray(response, ResponseFromCRMData.class);
		
		
		
	}
}
