package com.ld.sso.midlayer.dataconvert;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;

import com.ld.sso.crm.domain.CRMCustmemberModel;
import com.ld.sso.midlayer.databean.CRMCustmemberFullInfo;

@Service
public class CustModelToFullInfoConverter {
	
	public void convert(CRMCustmemberModel source, CRMCustmemberFullInfo destination){
		SimpleDateFormat dateFmt=new SimpleDateFormat("yyyy-MM-dd");        
		//2099-12-31
		
		destination.setCdmmaxdate((null != source.getCardmainModel() && null != source.getCardmainModel().getCdmmaxdate())? 
				dateFmt.format(source.getCardmainModel().getCdmmaxdate()):null);
		destination.setCdmmindate((null != source.getCardmainModel() && null != source.getCardmainModel().getCdmmindate())? 
						dateFmt.format(source.getCardmainModel().getCdmmindate()):null);
		destination.setCdmstatus(source.getCardmainModel().getCdmstatus());
		destination.setCdmtype(source.getCardmainModel().getCdmtype());
		
		destination.setCmaddr(source.getCmaddr());
		destination.setCmbirthday(null != source.getCmbirthday()?dateFmt.format(source.getCmbirthday()):null);
		destination.setCmcustid(source.getCmcustid());
		destination.setCmczz(null != source.getCmczz() ? source.getCmczz().intValue():null);
		destination.setCmemail(source.getCmemail());
		destination.setCmidno(source.getCmidno());
		destination.setCmidtype(source.getCmidtype());
		destination.setCmlczhye(source.getCmlczhye().setScale(2, BigDecimal.ROUND_HALF_DOWN).toString());
		destination.setCmmemid(source.getCmmemid());
		destination.setCmmobile1(source.getCmmobile1());
		destination.setCmname(source.getCmname());
		destination.setCmptname(source.getCmptname());
		destination.setCmreferee(source.getCmreferee());
		destination.setCmrelation(source.getCmrelation());
		destination.setCmsex(source.getCmsex());
		destination.setCmtotjf(null != source.getCmtotjf() ? source.getCmtotjf().intValue():null);
		
		destination.setCtname(source.getCustypeModel().getCtname());
	}
	
}
