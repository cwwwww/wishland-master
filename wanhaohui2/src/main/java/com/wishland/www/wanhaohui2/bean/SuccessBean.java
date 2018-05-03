package com.wishland.www.wanhaohui2.bean;

/**
 * Created by admin on 2017/10/22.
 */

public class SuccessBean {

    String type;
    String status;

    public SuccessBean(String status, String type) {
        this.status = status;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
