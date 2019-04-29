package com.ld.sso.oa.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ld.sso.oa.mapper.OAUserMapper;
import com.ld.sso.oa.model.OAUser;
import com.ld.sso.oa.mongo.dao.AttendenceAbroadDao;
import com.ld.sso.oa.mongo.dao.AttendenceDao;
import com.ld.sso.oa.mongo.dao.AttendenceReportDailyDao;
import com.ld.sso.oa.mongo.dao.AttendenceReportDao;
import com.ld.sso.oa.service.IOAService;


/**
 */
@Service
public class OAService implements IOAService {

	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private OAUserMapper oAUserMapper;
	@Autowired
	private AttendenceAbroadDao attendenceAbroadDao;
	@Autowired
	private AttendenceDao attendenceDao;
	@Autowired
	private AttendenceReportDailyDao attendenceReportDailyDao;
	@Autowired
	private AttendenceReportDao attendenceReportDao;
	
	//老手机号更改为新手机号，老手机的权益挪到新手机上
	@Override
	@Async //异步处理，调用被@Async标记的方法的调用者不能和被调用的方法在同一类中不然不会起作用
	public void changeUserMobile(String oldMobile, String newMobile) {
		logger.warn("~~OAService.changeUserMobile~~~start~~~~~~");
		logger.warn("~~oldMobile:"+oldMobile);
		logger.warn("~~newMobile:"+newMobile);
		//更新数据库
		int result = this.changeUserMobileInTable(oldMobile, newMobile);
		
		logger.warn("~~OAService.changeUserMobile~~~update db result:"+result);
				
		//更新考勤打卡数据，最好异步处理
		if(result > 0){
			this.changeUserMobileInMongo(oldMobile, newMobile);
		}
		
		logger.warn("~~OAService.changeUserMobile~~~end~~~~~~");
	}
	
	
	//数据库表中老手机号更改为新手机号
	public int changeUserMobileInTable(String oldMobile, String newMobile) {
		
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
	
	
	//考勤报表中，老手机号更改为新手机号
	//异步处理，调用被@Async标记的方法的调用者不能和被调用的方法在同一类中不然不会起作用
	@Async
	private void changeUserMobileInMongo(String oldMobile, String newMobile) {
		
		attendenceDao.changeMobile(oldMobile, newMobile);
		attendenceAbroadDao.changeMobile(oldMobile, newMobile);
		attendenceReportDailyDao.changeMobile(oldMobile, newMobile);
		attendenceReportDao.changeMobile(oldMobile, newMobile);
		
	}

}
