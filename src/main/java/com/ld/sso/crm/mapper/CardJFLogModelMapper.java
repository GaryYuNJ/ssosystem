package com.ld.sso.crm.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ld.sso.crm.domain.CardJFLogModel;

public interface CardJFLogModelMapper {
	
	CardJFLogModel selectCurJFYEByCustId(String cmcustid);
	
	CardJFLogModel selectCurJFYEByMobile(String mobile);
	
	BigDecimal selectJFSubTotalByCustId(@Param("cmcustid")String cmcustid);
	
	BigDecimal selectJFAddTotalByCustId(@Param("cmcustid")String cmcustid);
	
	List<CardJFLogModel> selectJFSubListByCustId(@Param("cmcustid")String cmcustid, @Param("startRow")int startRow, @Param("pageSize")int pageSize);
	List<CardJFLogModel> selectJFAddListByCustId(@Param("cmcustid")String cmcustid, @Param("startRow")int startRow, @Param("pageSize")int pageSize);
	List<CardJFLogModel> selectJFHistoryListByCustId(@Param("cmcustid")String cmcustid, @Param("startRow")int startRow, @Param("pageSize")int pageSize);
	
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