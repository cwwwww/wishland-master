package com.wishland.www.model.bean;

/**
 * Created by admin on 2017/9/29.
 */

public class KeyStoreBean {

    /**
     * status : 200
     * data : {"key":"814aeadb2cf5430092df63c3e612372b"}
     * token : M3M8O9o2V7Z8K2k2B1z0e3u4y4t143Q8p8M2L276n821f1p245f56597h3d8g5t518n158u4c3f7m3p3d6j8e7g820h1
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
