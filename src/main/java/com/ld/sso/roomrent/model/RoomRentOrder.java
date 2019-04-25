package com.ld.sso.roomrent.model;

import java.math.BigDecimal;
import java.util.Date;

public class RoomRentOrder {
    private Long orderId;

    private String rentType;

    private Long reserveId;

    private String projId;

    private String userId;

    private String orderNo;

    private Long shopNum;

    private Date startTime;

    private Date endTime;

    private BigDecimal money;

    private String orderStatus;

    private Date tradeTime;

    private String isBill;

    private String billRise;

    private String billTax;

    private String billType;

    private String billAddr;

    private String billPhone;

    private String billBank;

    private String billAccount;

    private String isCoupon;

    private String userCouponId;

    private String isIntegral;

    private Long usedIntegralNum;

    private String remark;

    private String contactPerson;

    private String contactPhone;

    private String accessBluetoothStatus;

    private Long accessBluetoothNum;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;

    private String recordState;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getRentType() {
        return rentType;
    }

    public void setRentType(String rentType) {
        this.rentType = rentType == null ? null : rentType.trim();
    }

    public Long getReserveId() {
        return reserveId;
    }

    public void setReserveId(Long reserveId) {
        this.reserveId = reserveId;
    }

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId == null ? null : projId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Long getShopNum() {
        return shopNum;
    }

    public void setShopNum(Long shopNum) {
        this.shopNum = shopNum;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getIsBill() {
        return isBill;
    }

    public void setIsBill(String isBill) {
        this.isBill = isBill == null ? null : isBill.trim();
    }

    public String getBillRise() {
        return billRise;
    }

    public void setBillRise(String billRise) {
        this.billRise = billRise == null ? null : billRise.trim();
    }

    public String getBillTax() {
        return billTax;
    }

    public void setBillTax(String billTax) {
        this.billTax = billTax == null ? null : billTax.trim();
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType == null ? null : billType.trim();
    }

    public String getBillAddr() {
        return billAddr;
    }

    public void setBillAddr(String billAddr) {
        this.billAddr = billAddr == null ? null : billAddr.trim();
    }

    public String getBillPhone() {
        return billPhone;
    }

    public void setBillPhone(String billPhone) {
        this.billPhone = billPhone == null ? null : billPhone.trim();
    }

    public String getBillBank() {
        return billBank;
    }

    public void setBillBank(String billBank) {
        this.billBank = billBank == null ? null : billBank.trim();
    }

    public String getBillAccount() {
        return billAccount;
    }

    public void setBillAccount(String billAccount) {
        this.billAccount = billAccount == null ? null : billAccount.trim();
    }

    public String getIsCoupon() {
        return isCoupon;
    }

    public void setIsCoupon(String isCoupon) {
        this.isCoupon = isCoupon == null ? null : isCoupon.trim();
    }

    public String getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(String userCouponId) {
        this.userCouponId = userCouponId == null ? null : userCouponId.trim();
    }

    public String getIsIntegral() {
        return isIntegral;
    }

    public void setIsIntegral(String isIntegral) {
        this.isIntegral = isIntegral == null ? null : isIntegral.trim();
    }

    public Long getUsedIntegralNum() {
        return usedIntegralNum;
    }

    public void setUsedIntegralNum(Long usedIntegralNum) {
        this.usedIntegralNum = usedIntegralNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson == null ? null : contactPerson.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public String getAccessBluetoothStatus() {
        return accessBluetoothStatus;
    }

    public void setAccessBluetoothStatus(String accessBluetoothStatus) {
        this.accessBluetoothStatus = accessBluetoothStatus == null ? null : accessBluetoothStatus.trim();
    }

    public Long getAccessBluetoothNum() {
        return accessBluetoothNum;
    }

    public void setAccessBluetoothNum(Long accessBluetoothNum) {
        this.accessBluetoothNum = accessBluetoothNum;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getRecordState() {
        return recordState;
    }

    public void setRecordState(String recordState) {
        this.recordState = recordState == null ? null : recordState.trim();
    }
}