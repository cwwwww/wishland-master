package com.wishland.www.wanhaohui2.bean;

/**
 * Created by admin on 2017/10/18.
 */

public class PersonInfoBean {
    private int status;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public class DataBean {
        private String uid;
        private String username;
        private String uname;
        private String uemail;
        private String umobile;
        private int usex;
        private String ubirthday;
        private String uwx;
        private String uqq;
        private String uaddr;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }

        public String getUemail() {
            return uemail;
        }

        public void setUemail(String uemail) {
            this.uemail = uemail;
        }

        public String getUmobile() {
            return umobile;
        }

        public void setUmobile(String umobile) {
            this.umobile = umobile;
        }

        public int getUsex() {
            return usex;
        }

        public void setUsex(int usex) {
            this.usex = usex;
        }

        public String getUbirthday() {
            return ubirthday;
        }

        public void setUbirthday(String ubirthday) {
            this.ubirthday = ubirthday;
        }

        public String getUwx() {
            return uwx;
        }

        public void setUwx(String uwx) {
            this.uwx = uwx;
        }

        public String getUqq() {
            return uqq;
        }

        public void setUqq(String uqq) {
            this.uqq = uqq;
        }

        public String getUaddr() {
            return uaddr;
        }

        public void setUaddr(String uaddr) {
            this.uaddr = uaddr;
        }
    }


}
