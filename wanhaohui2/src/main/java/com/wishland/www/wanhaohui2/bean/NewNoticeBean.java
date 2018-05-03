package com.wishland.www.wanhaohui2.bean;

import java.util.List;

/**
 * Created by admin on 2017/11/15.
 */

public class NewNoticeBean {
    private int status;
    private List<NewNoticeData> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<NewNoticeData> getData() {
        return data;
    }

    public void setData(List<NewNoticeData> data) {
        this.data = data;
    }

    public class NewNoticeData{
        private String title;
        private String msg;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
