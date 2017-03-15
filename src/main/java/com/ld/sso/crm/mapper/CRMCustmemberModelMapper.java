package com.ld.sso.crm.mapper;

import com.ld.sso.crm.domain.CRMCustmemberModel;


public interface CRMCustmemberModelMapper  {
    //int deleteByPrimaryKey(String cmmemid);

    //int insert(CRMCustmemberModel record);

    //int insertSelective(CRMCustmemberModel record);

    CRMCustmemberModel selectByPrimaryKey(String cmmemid);

    CRMCustmemberModel selectTokenInfoByPrimaryKey(String cmmemid);
    
    int updateByPrimaryKeySelective(CRMCustmemberModel record);
    int updateTokenByPrimaryKey(CRMCustmemberModel record);
    //int updateByPrimaryKey(CRMCustmemberModel record);
}