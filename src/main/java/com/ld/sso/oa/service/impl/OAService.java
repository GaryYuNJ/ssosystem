package com.ld.sso.oa.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ld.sso.oa.mapper.OAUserMapper;
import com.ld.sso.oa.model.OAUser;
import com.ld.sso.oa.service.IOAService;


/**
 */
@Service
public class OAService implements IOAService {

	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private OAUserMapper oAUserMapper;
	
	//老手机号更改为新手机号，老手机的权益挪到新手机上
	@Override
	public int changeUserMobile(String oldMobile, String newMobile) {
		
		//先查老手机号是否存在，不存在，直接返回
		OAUser userT = oAUserMapper.selectByMobile(oldMobile);
		
		if(null == userT || StringUtils.isEmpty(userT.getTelephone())){
			return 1;
			
		}else{

			//查询新手机号是否已在系统
			userT = oAUserMapper.selectByMobile(newMobile);
			//如果存在，新旧手机互换
			if(null != userT && !StringUtils.isEmpty(userT.getTelephone())){
				//先把新手机号对应的用户更新下
				//获取一串随机数字
				String newMobileT = (int)(1+Math.random()*(10-1+1))+"";
				
				//新旧手机号转换
				oAUserMapper.updateMobileByOldMobile(newMobile, newMobileT);
				oAUserMapper.updateMobileByOldMobile(oldMobile, newMobile);
				return	oAUserMapper.updateMobileByOldMobile(newMobileT, oldMobile);
				
			}else{
				//如果没有，直接更新
				// TODO Auto-generated method stub
				return oAUserMapper.updateMobileByOldMobile(oldMobile, newMobile);
			}
		}
		
	}
	

}
