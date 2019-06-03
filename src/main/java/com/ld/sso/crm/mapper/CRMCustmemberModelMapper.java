package com.ld.sso.crm.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ld.sso.crm.domain.CRMCustmemberModel;

import org.apache.ibatis.annotations.Param;


public interface CRMCustmemberModelMapper  {
    //int deleteByPrimaryKey(String cmmemid);

    //int insert(CRMCustmemberModel record);

    //int insertSelective(CRMCustmemberModel record);

    CRMCustmemberModel selectByPrimaryKey(String cmmemid);

    CRMCustmemberModel selectTokenInfoByPrimaryKey(String cmmemid);

    int updateByPrimaryKeySelective(CRMCustmemberModel record);
    int updateTokenByPrimaryKey(CRMCustmemberModel record);
    //int updateByPrimaryKey(CRMCustmemberModel record);

    void getEncryptedPasswd(Map params);

	void getDecryptedPasswd(Map params);

	CRMCustmemberModel selectBasicInfoByMobileAndEnPasswd(Map params);

	CRMCustmemberModel selectFullInfoByPrimaryKey(String cmmemid);

	int updateByMobileSelective(CRMCustmemberModel record);

	List<CRMCustmemberModel> selectMemberInfoAfterMaintainDate(@Param("cmmaintdate") Date lastMaintainDate,
                                                               @Param("startRow") int startRow,
                                                               @Param("pageSize") int pageSize);

	
	List<CRMCustmemberModel> selectMemberSelectedInfoAfterMaintainDate(@Param("cmmaintdate") Date lastMaintainDate,
														            @Param("startRow") int startRow,
														            @Param("pageSize") int pageSize);
	
	Integer selectMemberCountAfterMaintainDate(@Param("cmmaintdate") Date lastMaintainDate);
	
//	int updateMobileMemberId(CRMCustmemberModel record);
	
	CRMCustmemberModel selectByMobile(String mobile);

	CRMCustmemberModel selectSimpleInfoByMemId(String cmmemid);
}