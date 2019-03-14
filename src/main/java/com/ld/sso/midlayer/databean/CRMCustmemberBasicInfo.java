package com.ld.sso.midlayer.databean;

import java.io.Serializable;

public class CRMCustmemberBasicInfo implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String cmcustid; 
    private String cmmobile;
    private String cmmemid;
    
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
	public String getCmmemid() {
		return cmmemid;
	}
	public void setCmmemid(String cmmemid) {
		this.cmmemid = cmmemid;
	}
    
    
    
}