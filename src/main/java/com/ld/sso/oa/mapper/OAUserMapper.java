package com.ld.sso.oa.mapper;

import org.apache.ibatis.annotations.Param;

import com.ld.sso.oa.model.OAUser;

public interface OAUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(OAUser record);

    int insertSelective(OAUser record);

    OAUser selectByPrimaryKey(String id);
    
    OAUser selectByMobile(@Param("mobile")String mobile);

    int updateByPrimaryKeySelective(OAUser record);

    int updateByPrimaryKey(OAUser record);
    
    int updateMobileByOldMobile(@Param("oldMobile")String oldMobile, @Param("newMobile")String newMobile);
}