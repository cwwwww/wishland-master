package com.wishland.www.wanhaohui2.bean;

import java.util.Date;

/**
 * Created by admin on 2017/10/17.
 */

public class DateBean {
    private String time;
    private int date;

    public DateBean(String time, int date) {
        this.time = time;
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }


}
