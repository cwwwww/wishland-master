package com.wishland.www.model.bean;

/**
 * Created by admin on 2017/11/15.
 */

public class DomainBeanNew {

    /**
     * code : 1
     * msg : successful
     * data : {"domain_name":"https://xvns.yule-app.net"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * domain_name : https://xvns.yule-app.net
         */

        private String domain_name;

        public String getDomain_name() {
            return domain_name;
        }

        public void setDomain_name(String domain_name) {
            this.domain_name = domain_name;
        }
    }
}
