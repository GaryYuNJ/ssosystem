package com.ld.sso.midlayer.databean;

import java.io.Serializable;
import java.math.BigDecimal;

public class CRMCustmemberFullInfo implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String cmreferee; 
    private String cmrelation; 
    private String cmidno; 
    private String cmidtype; 
    private Integer cmtotjf; 
    private String cmptname; 
    private String cmcustid; 
    private String cmmobile;
    private String cmemail;
    private String cmmemid;
    private String cmaddr;
    private Integer cmczz;
    private String cmname; 
    private String cmbirthday; //2017-02-10
    private String cmsex;
    private String cmlczhye; //余额
    
    private String cdmmaxdate; //2099-12-31" ？？ cardmain
    private String cdmmindate;//2017-02-10 ？？ cardmain
    private String cdmtype; //表cardmain
    private String cdmstatus; // 表cardmain
    private String ctname; //表 custtype 
    
    
	public Integer getCmtotjf() {
		return cmtotjf;
	}
	public void setCmtotjf(Integer cmtotjf) {
		this.cmtotjf = cmtotjf;
	}
	public Integer getCmczz() {
		return cmczz;
	}
	public void setCmczz(Integer cmczz) {
		this.cmczz = cmczz;
	}
	public String getCmlczhye() {
		return cmlczhye;
	}
	public void setCmlczhye(String cmlczhye) {
		this.cmlczhye = cmlczhye;
	}
	public String getCmreferee() {
		return cmreferee;
	}
	public void setCmreferee(String cmreferee) {
		this.cmreferee = cmreferee;
	}
	public String getCmrelation() {
		return cmrelation;
	}
	public void setCmrelation(String cmrelation) {
		this.cmrelation = cmrelation;
	}
	public String getCmidno() {
		return cmidno;
	}
	public void setCmidno(String cmidno) {
		this.cmidno = cmidno;
	}
	public String getCmidtype() {
		return cmidtype;
	}
	public void setCmidtype(String cmidtype) {
		this.cmidtype = cmidtype;
	}
	public String getCdmstatus() {
		return cdmstatus;
	}
	public void setCdmstatus(String cdmstatus) {
		this.cdmstatus = cdmstatus;
	}
	public String getCmptname() {
		return cmptname;
	}
	public void setCmptname(String cmptname) {
		this.cmptname = cmptname;
	}
	public String getCmcustid() {
		return cmcustid;
	}
	public void setCmcustid(String cmcustid) {
		this.cmcustid = cmcustid;
	}
	
	public String getCmmobile() {
		return cmmobile;
	}
	public void setCmmobile(String cmmobile) {
		this.cmmobile = cmmobile;
	}
	public String getCtname() {
		return ctname;
	}
	public void setCtname(String ctname) {
		this.ctname = ctname;
	}
	public String getCmemail() {
		return cmemail;
	}
	public void setCmemail(String cmemail) {
		this.cmemail = cmemail;
	}
	public String getCmmemid() {
		return cmmemid;
	}
	public void setCmmemid(String cmmemid) {
		this.cmmemid = cmmemid;
	}
	public String getCmaddr() {
		return cmaddr;
	}
	public void setCmaddr(String cmaddr) {
		this.cmaddr = cmaddr;
	}
	public String getCdmtype() {
		return cdmtype;
	}
	public void setCdmtype(String cdmtype) {
		this.cdmtype = cdmtype;
	}
	public String getCmname() {
		return cmname;
	}
	public void setCmname(String cmname) {
		this.cmname = cmname;
	}
	public String getCmbirthday() {
		return cmbirthday;
	}
	public void setCmbirthday(String cmbirthday) {
		this.cmbirthday = cmbirthday;
	}
	public String getCmsex() {
		return cmsex;
	}
	public void setCmsex(String cmsex) {
		this.cmsex = cmsex;
	}
	public String getCdmmaxdate() {
		return cdmmaxdate;
	}
	public void setCdmmaxdate(String cdmmaxdate) {
		this.cdmmaxdate = cdmmaxdate;
	}
	public String getCdmmindate() {
		return cdmmindate;
	}
	public void setCdmmindate(String cdmmindate) {
		this.cdmmindate = cdmmindate;
	}
    
}