package com.ld.sso.crm.util;

import java.io.UnsupportedEncodingException;

public class CRMCharacterConverter {

	
	//oracle数据库中文编码是iso-8859-1，需要转成utf-8才能存储到mysql
	public static String convert8859P1ToGBK(String s)
    {
		String result =null;
		try {
			byte[] buf = s.getBytes("ISO-8859-1");
			result = new String(buf,"GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
    }
	
	//oracle数据库中文编码是iso-8859-1，需要转成utf-8才能存储到mysql
	public static String convertGBKTo8859P1(String s)
    {
		String result =null;
		try {
			byte[] buf = s.getBytes("GBK");
			result = new String(buf,"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
    }
	
}
