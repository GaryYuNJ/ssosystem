package com.ld.sso.crm.domain;

import java.math.BigDecimal;
import java.util.Date;

public class CardJFLogModelKey {
    private Date cdldate;

    private BigDecimal cdlseqno;

    public Date getCdldate() {
        return cdldate;
    }

    public void setCdldate(Date cdldate) {
        this.cdldate = cdldate;
    }

    public BigDecimal getCdlseqno() {
        return cdlseqno;
    }

    public void setCdlseqno(BigDecimal cdlseqno) {
        this.cdlseqno = cdlseqno;
    }
}