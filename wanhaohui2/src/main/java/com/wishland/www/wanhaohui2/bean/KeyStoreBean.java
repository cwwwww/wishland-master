package com.wishland.www.wanhaohui2.bean;

/**
 * Created by admin on 2017/10/13.
 */

public class KeyStoreBean {
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
         * key : 814aeadb2cf5430092df63c3e612372b
         */

        private String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
