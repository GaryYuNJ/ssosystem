package com.ld.sso.oa.mongo.bo;

import java.io.*;
import org.springframework.data.mongodb.core.mapping.*;
import org.springframework.data.annotation.*;

@Document(collection = "attendenceAbroad")
public class AttendenceBoAbroad implements Serializable
{
    private static final long serialVersionUID = 2928923917001675021L;
    @Id
    private String id;
    private String attendence_date;
    private String name;
    private String mobile;
    private Long created_at;
    private Long attendence_time;
    private String device_info;
    private String adder_longitude;
    private String adder_latitude;
    private String address;
    
    public String getId() {
        return this.id;
    }
    
    public void setId(final String id) {
        this.id = id;
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
    
    public static long getSerialversionuid() {
        return 2928923917001675021L;
    }
    
    public String getAdder_longitude() {
        return this.adder_longitude;
    }
    
    public void setAdder_longitude(final String adder_longitude) {
        this.adder_longitude = adder_longitude;
    }
    
    public String getAdder_latitude() {
        return this.adder_latitude;
    }
    
    public void setAdder_latitude(final String adder_latitude) {
        this.adder_latitude = adder_latitude;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(final String address) {
        this.address = address;
    }
}
