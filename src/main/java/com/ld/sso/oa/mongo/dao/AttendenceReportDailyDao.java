package com.ld.sso.oa.mongo.dao;

import org.springframework.stereotype.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.beans.factory.annotation.*;
import org.slf4j.*;

import com.ld.sso.oa.mongo.bo.AttendenceReportDaily;

import org.springframework.data.mongodb.core.query.*;


@Repository
public class AttendenceReportDailyDao
{
    private static final Logger logger;
    @Autowired
    private MongoTemplate mongoTemplate;
    
    static {
        logger = LoggerFactory.getLogger((Class)AttendenceReportDailyDao.class);
    }
    
   public void changeMobile(String oldMobile, String newMobile){
    	
    	logger.warn("~~~~~~AttendenceReportDailyDao.changeMobile~~~~~~start");
    	logger.warn("~~~~~~oldMobile~~~~~~:"+oldMobile);
    	logger.warn("~~~~~~newMobile~~~~~~:"+newMobile);
    	
    	final Criteria criatira = Criteria.where("mobile").is((Object)oldMobile);
        final Query query = new Query();
        
        query.addCriteria((CriteriaDefinition)criatira);
        //Update update = Update.update("mobile", newMobile).set("visitCount", 10);
        Update update = new Update();
        update.set("mobile", newMobile);
        
    	this.mongoTemplate.updateMulti(query, update, (Class)AttendenceReportDaily.class);
    	logger.warn("~~~~~~changeMobile~~~~~~end");
    }
    
}
