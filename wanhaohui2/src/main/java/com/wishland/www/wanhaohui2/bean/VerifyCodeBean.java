package com.wishland.www.wanhaohui2.bean;

/**
 * Created by admin on 2017/11/12.
 */

public class VerifyCodeBean {

    /**
     * status : 200
     * data : {"verify":true}
     */

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

    public static class DataBean {
        /**
         * verify : true
         */

        private boolean verify;

        public boolean isVerify() {
            return verify;
        }

        public void setVerify(boolean verify) {
            this.verify = verify;
        }
    }
}
