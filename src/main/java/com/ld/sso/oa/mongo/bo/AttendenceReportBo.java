package com.ld.sso.oa.mongo.bo;

import java.io.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.data.annotation.*;
import java.util.*;

@Document(collection = "attendenceReportMonth")
public class AttendenceReportBo implements Serializable
{
    private static final long serialVersionUID = 2928923917001675021L;
    @Id
    private String id;
    private String month;
    private String mobile;
    private String realName;
    private String regionalCompany;
    private String regionalCompanyId;
    private String company;
    private String companyId;
    private String userId;
    private String department;
    private String departmentId;
    private String project;
    private String amWorkTime;
    private String pmWorkTime;
    private String workTime;
    private Long updateTime;
    private Map<String, String> attendenceDetail;
    private int needWorkDays;
    private int realWorkDays;
    private int invalidWorkDays;
    private String remark;
    private int deviceTotal;
    private List<String> deviceList;
    private int sequence;
    private int unCheckAmount;
    private int lateAmount;
    private int earlyLeaveAmount;
    
    public String getRegionalCompany() {
        return this.regionalCompany;
    }
    
    public void setRegionalCompany(final String regionalCompany) {
        this.regionalCompany = regionalCompany;
    }
    
    public String getRegionalCompanyId() {
        return this.regionalCompanyId;
    }
    
    public void setRegionalCompanyId(final String regionalCompanyId) {
        this.regionalCompanyId = regionalCompanyId;
    }
    
    public int getUnCheckAmount() {
        return this.unCheckAmount;
    }
    
    public void setUnCheckAmount(final int unCheckAmount) {
        this.unCheckAmount = unCheckAmount;
    }
    
    public int getLateAmount() {
        return this.lateAmount;
    }
    
    public void setLateAmount(final int lateAmount) {
        this.lateAmount = lateAmount;
    }
    
    public int getEarlyLeaveAmount() {
        return this.earlyLeaveAmount;
    }
    
    public void setEarlyLeaveAmount(final int earlyLeaveAmount) {
        this.earlyLeaveAmount = earlyLeaveAmount;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public String getMonth() {
        return this.month;
    }
    
    public void setMonth(final String month) {
        this.month = month;
    }
    
    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(final String mobile) {
        this.mobile = mobile;
    }
    
    public String getRealName() {
        return this.realName;
    }
    
    public void setRealName(final String realName) {
        this.realName = realName;
    }
    
    public String getCompany() {
        return this.company;
    }
    
    public void setCompany(final String company) {
        this.company = company;
    }
    
    public String getDepartment() {
        return this.department;
    }
    
    public void setDepartment(final String department) {
        this.department = department;
    }
    
    public String getProject() {
        return this.project;
    }
    
    public void setProject(final String project) {
        this.project = project;
    }
    
    public String getAmWorkTime() {
        return this.amWorkTime;
    }
    
    public void setAmWorkTime(final String amWorkTime) {
        this.amWorkTime = amWorkTime;
    }
    
    public String getPmWorkTime() {
        return this.pmWorkTime;
    }
    
    public void setPmWorkTime(final String pmWorkTime) {
        this.pmWorkTime = pmWorkTime;
    }
    
    public String getWorkTime() {
        return this.workTime;
    }
    
    public void setWorkTime(final String workTime) {
        this.workTime = workTime;
    }
    
    public Long getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(final Long updateTime) {
        this.updateTime = updateTime;
    }
    
    public Map<String, String> getAttendenceDetail() {
        return this.attendenceDetail;
    }
    
    public void setAttendenceDetail(final Map<String, String> attendenceDetail) {
        this.attendenceDetail = attendenceDetail;
    }
    
    public int getNeedWorkDays() {
        return this.needWorkDays;
    }
    
    public void setNeedWorkDays(final int needWorkDays) {
        this.needWorkDays = needWorkDays;
    }
    
    public int getRealWorkDays() {
        return this.realWorkDays;
    }
    
    public void setRealWorkDays(final int realWorkDays) {
        this.realWorkDays = realWorkDays;
    }
    
    public int getInvalidWorkDays() {
        return this.invalidWorkDays;
    }
    
    public void setInvalidWorkDays(final int invalidWorkDays) {
        this.invalidWorkDays = invalidWorkDays;
    }
    
    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(final String remark) {
        this.remark = remark;
    }
    
    public int getDeviceTotal() {
        return this.deviceTotal;
    }
    
    public void setDeviceTotal(final int deviceTotal) {
        this.deviceTotal = deviceTotal;
    }
    
    public List<String> getDeviceList() {
        return this.deviceList;
    }
    
    public void setDeviceList(final List<String> deviceList) {
        this.deviceList = deviceList;
    }
    
    public int getSequence() {
        return this.sequence;
    }
    
    public void setSequence(final int sequence) {
        this.sequence = sequence;
    }
    
    public String getCompanyId() {
        return this.companyId;
    }
    
    public void setCompanyId(final String companyId) {
        this.companyId = companyId;
    }
    
    public String getDepartmentId() {
        return this.departmentId;
    }
    
    public void setDepartmentId(final String departmentId) {
        this.departmentId = departmentId;
    }
    
    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(final String userId) {
        this.userId = userId;
    }
}
