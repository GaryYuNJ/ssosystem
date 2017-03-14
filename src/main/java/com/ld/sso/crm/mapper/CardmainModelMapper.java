package com.ld.sso.crm.mapper;

import com.ld.sso.crm.domain.CardmainModel;

public interface CardmainModelMapper {
    //int deleteByPrimaryKey(String cdmno);

    //int insert(CardmainModel record);

    //int insertSelective(CardmainModel record);

    CardmainModel selectByPrimaryKey(String cdmno);

    //int updateByPrimaryKeySelective(CardmainModel record);

    //int updateByPrimaryKey(CardmainModel record);
}