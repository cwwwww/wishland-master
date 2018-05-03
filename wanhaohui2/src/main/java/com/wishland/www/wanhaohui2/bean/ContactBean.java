package com.wishland.www.wanhaohui2.bean;

import java.util.List;

/**
 * Created by admin on 2017/10/25.
 */

public class ContactBean {
    private int status;
    private ContactData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ContactData getData() {
        return data;
    }

    public void setData(ContactData data) {
        this.data = data;
    }

    public class ContactData{
        private String qq;
        private String qqurl;
        private String wx;
        private String wxurl;
        private String skype;
        private String skypeurl;
        private String email;
        private List<ServiceData> service;

        public String getQqurl() {
            return qqurl;
        }

        public void setQqurl(String qqurl) {
            this.qqurl = qqurl;
        }

        public String getWxurl() {
            return wxurl;
        }

        public void setWxurl(String wxurl) {
            this.wxurl = wxurl;
        }

        public String getSkypeurl() {
            return skypeurl;
        }

        public void setSkypeurl(String skypeurl) {
            this.skypeurl = skypeurl;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getWx() {
            return wx;
        }

        public void setWx(String wx) {
            this.wx = wx;
        }

        public String getSkype() {
            return skype;
        }

        public void setSkype(String skype) {
            this.skype = skype;
        }

        public List<ServiceData> getService() {
            return service;
        }

        public void setService(List<ServiceData> service) {
            this.service = service;
        }

        public class ServiceData{
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
}
