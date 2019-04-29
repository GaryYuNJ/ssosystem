package com.ld.sso.oa.mongo.bo;

import java.io.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.data.annotation.*;

@Document(collection = "attendence")
public class AttendenceBo implements Serializable
{
    private static final long serialVersionUID = 2928923917001675021L;
    @Id
    private String id;
    private String bluetooth_name;
    private String attendence_date;
    private String name;
    private String mobile;
    private Long created_at;
    private Long attendence_time;
    private String device_info;
    private String tag;
    private String mac_addr;
    
    public String getId() {
        return this.id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public String getBluetooth_name() {
        return this.bluetooth_name;
    }
    
    public void setBluetooth_name(final String bluetooth_name) {
        this.bluetooth_name = bluetooth_name;
    }
    
    public String getAttendence_date() {
        return this.attendence_date;
    }
    
    public void setAttendence_date(final String attendence_date) {
        this.attendence_date = attendence_date;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public String getMobile() {
        return this.mobile;
    }
    
    public void setMobile(final String mobile) {
        this.mobile = mobile;
    }
    
    public Long getCreated_at() {
        return this.created_at;
    }
    
    public void setCreated_at(final Long created_at) {
        this.created_at = created_at;
    }
    
    public Long getAttendence_time() {
        return this.attendence_time;
    }
    
    public void setAttendence_time(final Long attendence_time) {
        this.attendence_time = attendence_time;
    }
    
    public String getDevice_info() {
        return this.device_info;
    }
    
    public void setDevice_info(final String device_info) {
        this.device_info = device_info;
    }
    
    public String getTag() {
        return this.tag;
    }
    
    public void setTag(final String tag) {
        this.tag = tag;
    }
    
    public String getMac_addr() {
        return this.mac_addr;
    }
    
    public void setMac_addr(final String mac_addr) {
        this.mac_addr = mac_addr;
    }
    
    public static long getSerialversionuid() {
        return 2928923917001675021L;
    }
}
