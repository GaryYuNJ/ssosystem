package com.ld.sso.oa.mongo.bo;

import java.io.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.data.annotation.*;
import java.util.*;

@Document(collection = "attendenceReportDaily")
public class AttendenceReportDaily implements Serializable
{
    private static final long serialVersionUID = 2928923917001675021L;
    @Id
    private String id;
    private String regionalCompany;
    private String regionalCompanyId;
    private String date;
    private String month;
    private String mobile;
    private long updateTime;
    private String userName;
    private String userId;
    private String companyName;
    private String companyId;
    private String departmentName;
    private String departmentId;
    private String projectName;
    private int sequence;
    private String amWorkTime;
    private String pmWorkTime;
    private String workTime;
    private String amCheckTime;
    private String pmCheckTime;
    private String amStatus;
    private String pmStatus;
    private String amWorkStatus;
    private String pmWorkStatus;
    private int deviceTotal;
    private List<String> deviceList;
    private int amNormalTime;
    private int pmNormalTime;
    private int unCheckAmount;
    private int lateAmount;
    private int earlyLeaveAmount;
    private int outWorkAmount;
    
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
    
    public long getUpdateTime() {
        return this.updateTime;
    }
    
    public void setUpdateTime(final long updateTime) {
        this.updateTime = updateTime;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public String getDate() {
        return this.date;
    }
    
    public void setDate(final String date) {
        this.date = date;
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
    
    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(final String userName) {
        this.userName = userName;
    }
    
    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(final String userId) {
        this.userId = userId;
    }
    
    public String getCompanyName() {
        return this.companyName;
    }
    
    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }
    
    public String getCompanyId() {
        return this.companyId;
    }
    
    public void setCompanyId(final String companyId) {
        this.companyId = companyId;
    }
    
    public String getDepartmentName() {
        return this.departmentName;
    }
    
    public void setDepartmentName(final String departmentName) {
        this.departmentName = departmentName;
    }
    
    public String getDepartmentId() {
        return this.departmentId;
    }
    
    public void setDepartmentId(final String departmentId) {
        this.departmentId = departmentId;
    }
    
    public String getProjectName() {
        return this.projectName;
    }
    
    public void setProjectName(final String projectName) {
        this.projectName = projectName;
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
    
    public String getAmCheckTime() {
        return this.amCheckTime;
    }
    
    public void setAmCheckTime(final String amCheckTime) {
        this.amCheckTime = amCheckTime;
    }
    
    public String getPmCheckTime() {
        return this.pmCheckTime;
    }
    
    public void setPmCheckTime(final String pmCheckTime) {
        this.pmCheckTime = pmCheckTime;
    }
    
    public String getAmStatus() {
        return this.amStatus;
    }
    
    public void setAmStatus(final String amStatus) {
        this.amStatus = amStatus;
    }
    
    public String getPmStatus() {
        return this.pmStatus;
    }
    
    public void setPmStatus(final String pmStatus) {
        this.pmStatus = pmStatus;
    }
    
    public String getAmWorkStatus() {
        return this.amWorkStatus;
    }
    
    public void setAmWorkStatus(final String amWorkStatus) {
        this.amWorkStatus = amWorkStatus;
    }
    
    public String getPmWorkStatus() {
        return this.pmWorkStatus;
    }
    
    public void setPmWorkStatus(final String pmWorkStatus) {
        this.pmWorkStatus = pmWorkStatus;
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
    
    public int getOutWorkAmount() {
        return this.outWorkAmount;
    }
    
    public void setOutWorkAmount(final int outWorkAmount) {
        this.outWorkAmount = outWorkAmount;
    }
    
    public int getAmNormalTime() {
        return this.amNormalTime;
    }
    
    public void setAmNormalTime(final int amNormalTime) {
        this.amNormalTime = amNormalTime;
    }
    
    public int getPmNormalTime() {
        return this.pmNormalTime;
    }
    
    public void setPmNormalTime(final int pmNormalTime) {
        this.pmNormalTime = pmNormalTime;
    }
}
