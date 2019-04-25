package com.ld.sso.roomrent.mapper;

import org.apache.ibatis.annotations.Param;

import com.ld.sso.roomrent.model.RoomRentUser;

public interface RoomRentUserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(RoomRentUser record);

    int insertSelective(RoomRentUser record);

    RoomRentUser selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(RoomRentUser record);

    int updateByPrimaryKey(RoomRentUser record);
    
    int updateMobileByOldMobile(@Param("oldMobile")String oldMobile, @Param("newMobile")String newMobile);
    RoomRentUser selectByMobile(@Param("mobile")String mobile);
}