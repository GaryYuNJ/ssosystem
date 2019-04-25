package com.ld.sso.roomrent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ld.sso.roomrent.mapper.RoomRentOrderMapper;
import com.ld.sso.roomrent.mapper.RoomRentUserMapper;
import com.ld.sso.roomrent.model.RoomRentUser;
import com.ld.sso.roomrent.service.IRoomRentService;


/**
 */
@Service
public class RoomRentService implements IRoomRentService {

	@Autowired
	private RoomRentUserMapper roomRentUserMapper;
	
	@Autowired
	private RoomRentOrderMapper roomRentOrderMapper;
	
	@Override
	public int changeUserMobile(String oldMobile, String newMobile) {
		// TODO Auto-generated method stub
		//先查老手机号是否存在，不存在，直接返回
		RoomRentUser userT = roomRentUserMapper.selectByMobile(oldMobile);
		
		if(null == userT || StringUtils.isEmpty(userT.getUserPhone())){
			return 1;
			
		}else{
			//查询新手机号是否已在系统
			userT = roomRentUserMapper.selectByMobile(newMobile);
			
			//订单联系人先改
			roomRentOrderMapper.updateMobileByOldMobile(oldMobile, newMobile);
			
			//如果存在，新旧手机互换
			if(null != userT && !StringUtils.isEmpty(userT.getUserPhone())){
				//先把新手机号对应的用户更新下
				//获取一串随机数字
				String newMobileT = (int)(1+Math.random()*(10-1+1))+"";
				
				//新旧手机号转换
				roomRentUserMapper.updateMobileByOldMobile(newMobile, newMobileT);
				roomRentUserMapper.updateMobileByOldMobile(oldMobile, newMobile);
				return	roomRentUserMapper.updateMobileByOldMobile(newMobileT, oldMobile);
				
			}else{
				//如果没有，直接更新
				// TODO Auto-generated method stub
				return roomRentUserMapper.updateMobileByOldMobile(oldMobile, newMobile);
			}
		}
		
	}
}
