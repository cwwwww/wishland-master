package com.wishland.www.wanhaohui2.bean;

/**
 * Created by admin on 2017/11/10.
 */

public class CodeBean {


    /**
     * status : 200
     * data : {"wait":60,"code":5866}
     * token : J5P0M9d7n6Y9M5D3G4U755U4h1j932Z3r6o2L379e0h3q8c931q1s5v857g377l3g91235k976a9h74935v6l8104744
     */

    private int status;
    private DataBean data;
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class DataBean {
        /**
         * wait : 60
         * code : 5866
         */

        private int wait;
        private String code;

        public int getWait() {
            return wait;
        }

        public void setWait(int wait) {
            this.wait = wait;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
