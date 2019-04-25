package com.ld.sso.oa.model;

import java.util.Date;

public class OAUser {
    private String id;

    private Boolean locked;

    private String organizationId;

    private String password;

    private String salt;

    private String username;

    private Date createtime;

    private String createuser;

    private String icon;

    private Date modifytime;

    private String modifyuser;

    private String userno;

    private Integer status;

    private String superior;

    private String telephone;

    private Integer fromtype;

    private Integer breakfast;

    private Integer dinner;

    private String realname;

    private Integer isregist;

    private String attendanceGroupId;

    private String projectName;

    private String recordState;

    private Integer sort;

    private Integer orgsort;

    private Date jobTime;

    private Date quitTime;

    private String businessTravel;

    private String manufacture;

    private String phonemodel;

    private String udid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId == null ? null : organizationId.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser == null ? null : createuser.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public String getModifyuser() {
        return modifyuser;
    }

    public void setModifyuser(String modifyuser) {
        this.modifyuser = modifyuser == null ? null : modifyuser.trim();
    }

    public String getUserno() {
        return userno;
    }

    public void setUserno(String userno) {
        this.userno = userno == null ? null : userno.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSuperior() {
        return superior;
    }

    public void setSuperior(String superior) {
        this.superior = superior == null ? null : superior.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public Integer getFromtype() {
        return fromtype;
    }

    public void setFromtype(Integer fromtype) {
        this.fromtype = fromtype;
    }

    public Integer getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Integer breakfast) {
        this.breakfast = breakfast;
    }

    public Integer getDinner() {
        return dinner;
    }

    public void setDinner(Integer dinner) {
        this.dinner = dinner;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public Integer getIsregist() {
        return isregist;
    }

    public void setIsregist(Integer isregist) {
        this.isregist = isregist;
    }

    public String getAttendanceGroupId() {
        return attendanceGroupId;
    }

    public void setAttendanceGroupId(String attendanceGroupId) {
        this.attendanceGroupId = attendanceGroupId == null ? null : attendanceGroupId.trim();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getRecordState() {
        return recordState;
    }

    public void setRecordState(String recordState) {
        this.recordState = recordState == null ? null : recordState.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getOrgsort() {
        return orgsort;
    }

    public void setOrgsort(Integer orgsort) {
        this.orgsort = orgsort;
    }

    public Date getJobTime() {
        return jobTime;
    }

    public void setJobTime(Date jobTime) {
        this.jobTime = jobTime;
    }

    public Date getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(Date quitTime) {
        this.quitTime = quitTime;
    }

    public String getBusinessTravel() {
        return businessTravel;
    }

    public void setBusinessTravel(String businessTravel) {
        this.businessTravel = businessTravel == null ? null : businessTravel.trim();
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture == null ? null : manufacture.trim();
    }

    public String getPhonemodel() {
        return phonemodel;
    }

    public void setPhonemodel(String phonemodel) {
        this.phonemodel = phonemodel == null ? null : phonemodel.trim();
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid == null ? null : udid.trim();
    }
}