package com.ld.sso.oa.mongo.dao;

import org.springframework.stereotype.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.beans.factory.annotation.*;
import com.ld.sso.oa.mongo.bo.AttendenceBoAbroad;
import org.springframework.data.mongodb.core.query.*;
import org.slf4j.*;

@Repository
public class AttendenceAbroadDao
{
    private static final Logger logger;
    @Autowired
    private MongoTemplate mongoTemplate;
    

    public void changeMobile(String oldMobile, String newMobile){
    	
    	logger.warn("~~~~~~AttendenceAbroadDao.changeMobile~~~~~~start");
    	logger.warn("~~~~~~oldMobile~~~~~~:"+oldMobile);
    	logger.warn("~~~~~~newMobile~~~~~~:"+newMobile);
    	
    	final Criteria criatira = Criteria.where("mobile").is((Object)oldMobile);
        final Query query = new Query();
        
        query.addCriteria((CriteriaDefinition)criatira);
        //Update update = Update.update("mobile", oldMobile).set("mobile", newMobile);
        Update update = new Update();
        update.set("mobile", newMobile);
        
    	this.mongoTemplate.updateMulti(query, update, (Class)AttendenceBoAbroad.class);
    	logger.warn("~~~~~~changeMobile~~~~~~end");
    }
    
    
    static {
        logger = LoggerFactory.getLogger((Class)AttendenceAbroadDao.class);
    }
}
