package com.ld.sso.oa.mongo.bo;

import java.util.*;

public class Pager
{
    private int pageSize;
    private int pageNum;
    private int pageCount;
    private long total;
    private List result;
    
    public Pager() {
        this.pageSize = 10;
        this.pageNum = 1;
    }
    
    public int getPageSize() {
        return this.pageSize;
    }
    
    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }
    
    public int getPageNum() {
        return this.pageNum;
    }
    
    public void setPageNum(final int pageNum) {
        this.pageNum = pageNum;
    }
    
    public int getPageCount() {
        if (this.total > 0L) {
            this.pageCount = (int)((this.total % this.pageSize > 0L) ? (this.total / this.pageSize + 1L) : (this.total / this.pageSize));
        }
        return this.pageCount;
    }
    
    public void setPageCount(final int pageCount) {
        this.pageCount = pageCount;
    }
    
    public long getTotal() {
        return this.total;
    }
    
    public void setTotal(final long total) {
        this.total = total;
    }
    
    public List getResult() {
        return this.result;
    }
    
    public void setResult(final List result) {
        this.result = result;
    }
}
