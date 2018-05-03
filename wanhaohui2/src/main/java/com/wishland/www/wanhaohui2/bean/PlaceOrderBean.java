package com.wishland.www.wanhaohui2.bean;

import java.io.Serializable;

/**
 * Created by admin on 2017/11/1.
 */

public class PlaceOrderBean implements Serializable {
    private String startTime;
    private String endTime;
    private String type;
    private String name;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlaceOrderBean(String startTime, String endTime, String type, String name) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.name = name;
    }
}
