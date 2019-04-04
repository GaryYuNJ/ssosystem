package com.ld.sso.core.mapper;

import org.apache.ibatis.annotations.Param;

import com.ld.sso.core.model.SmsCode;

public interface SmsCodeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SmsCode record);

    int insertSelective(SmsCode record);

    SmsCode selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SmsCode record);

    int updateByPrimaryKey(SmsCode record);
    
    SmsCode selectValidByMobileAndCode(@Param("mobile")String mobile, @Param("code")String code);
}