package com.ld.sso.roomrent.mapper;

import org.apache.ibatis.annotations.Param;

import com.ld.sso.oa.model.OAUser;
import com.ld.sso.roomrent.model.RoomRentOrder;

public interface RoomRentOrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(RoomRentOrder record);

    int insertSelective(RoomRentOrder record);

    RoomRentOrder selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(RoomRentOrder record);

    int updateByPrimaryKey(RoomRentOrder record);
    
    int updateMobileByOldMobile(@Param("oldMobile")String oldMobile, @Param("newMobile")String newMobile);

	
}