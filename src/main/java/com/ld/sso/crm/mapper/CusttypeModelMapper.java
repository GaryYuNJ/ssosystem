package com.ld.sso.crm.mapper;

import com.ld.sso.crm.domain.CusttypeModel;

public interface CusttypeModelMapper {
    //int deleteByPrimaryKey(String ctcode);

    //int insert(CusttypeModel record);

    //int insertSelective(CusttypeModel record);

    CusttypeModel selectByPrimaryKey(String ctcode);

    int updateByPrimaryKeySelective(CusttypeModel record);

    //int updateByPrimaryKey(CusttypeModel record);
}