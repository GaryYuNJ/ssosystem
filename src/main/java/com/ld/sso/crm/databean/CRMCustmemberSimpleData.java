package com.ld.sso.crm.databean;

import java.io.Serializable;
import java.util.Date;

public class CRMCustmemberSimpleData implements Serializable  {

	private static final long serialVersionUID = 1L;

	private String cmmemid;

    private String cmcustid;

    private Date cmmaintdate;

    private String cmname;

    private String cmmobile;

    private String cmptname;

	public String getCmmemid() {
		return cmmemid;
	}

	public void setCmmemid(String cmmemid) {
		this.cmmemid = cmmemid;
	}

	public String getCmcustid() {
		return cmcustid;
	}

	public void setCmcustid(String cmcustid) {
		this.cmcustid = cmcustid;
	}

	public Date getCmmaintdate() {
		return cmmaintdate;
	}

	public void setCmmaintdate(Date cmmaintdate) {
		this.cmmaintdate = cmmaintdate;
	}

	public String getCmname() {
		return cmname;
	}

	public void setCmname(String cmname) {
		this.cmname = cmname;
	}


	public String getCmmobile() {
		return cmmobile;
	}

	public void setCmmobile(String cmmobile) {
		this.cmmobile = cmmobile;
	}

	public String getCmptname() {
		return cmptname;
	}

	public void setCmptname(String cmptname) {
		this.cmptname = cmptname;
	}

    
}