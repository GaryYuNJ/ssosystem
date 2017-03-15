package com.ld.sso.midlayer.databean;

import java.io.Serializable;

public class CRMCustmemberBasicInfo implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String cmcustid; 
    private String cmmobile1;
    private String cmmemid;
    
	public String getCmcustid() {
		return cmcustid;
	}
	public void setCmcustid(String cmcustid) {
		this.cmcustid = cmcustid;
	}
	public String getCmmobile1() {
		return cmmobile1;
	}
	public void setCmmobile1(String cmmobile1) {
		this.cmmobile1 = cmmobile1;
	}
	public String getCmmemid() {
		return cmmemid;
	}
	public void setCmmemid(String cmmemid) {
		this.cmmemid = cmmemid;
	}
    
    
    
}