package com.wishland.www.wanhaohui2.bean;

import java.util.List;

/**
 * Created by admin on 2017/10/19.
 */

public class ServiceBean {

    /**
     * status : 200
     * data : [{"code":"service","content":"https://chat6.livechatvalue.com/chat/chatClient/chatbox.jsp?companyID=17779&configID=48708&jid=&s=1"},{"code":"service_gw","content":"https://chat6.livechatvalue.com/chat/chatClient/chatbox.jsp?companyID=17779&configID=48708&jid=&s=1"}]
     */

    private int status;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * code : service
         * content : https://chat6.livechatvalue.com/chat/chatClient/chatbox.jsp?companyID=17779&configID=48708&jid=&s=1
         */

        private String code;
        private String content;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
