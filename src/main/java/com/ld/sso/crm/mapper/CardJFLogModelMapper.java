package com.ld.sso.crm.mapper;

import com.ld.sso.crm.domain.CardJFLogModel;

public interface CardJFLogModelMapper {
	
	CardJFLogModel selectCurJFYEByCustId(String cmcustid);
	
//    int deleteByPrimaryKey(CardJFLogModelKey key);
//
//    int insert(CardJFLogModel record);
//
//    int insertSelective(CardJFLogModel record);
//
//    CardJFLogModel selectByPrimaryKey(CardJFLogModelKey key);
//
//    int updateByPrimaryKeySelective(CardJFLogModel record);
//
//    int updateByPrimaryKey(CardJFLogModel record);
}