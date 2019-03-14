package com.ld.sso.crm.mapper;

import com.ld.sso.crm.domain.CustomerModel;

public interface CustomerModelMapper {
    int deleteByPrimaryKey(String cid);

    int insert(CustomerModel record);

    int insertSelective(CustomerModel record);

    CustomerModel selectByPrimaryKey(String cid);

    int updateByPrimaryKeySelective(CustomerModel record);

    int updateByPrimaryKey(CustomerModel record);
}