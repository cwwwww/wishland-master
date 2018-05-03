package com.wishland.www.wanhaohui2.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/21.
 */

public class MessageType implements Serializable {
    private String starttime;
    private String endtime;
    private int content;
    private String name;
    private int type;

    public MessageType(String starttime, String endtime, int content, int type, String name) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.content = content;
        this.type = type;
        this.name = name;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MessageType{" +
                "starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                ", content='" + content + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
