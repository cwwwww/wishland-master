package com.wishland.www.wanhaohui2.bean;

/**
 * Created by admin on 2017/11/14.
 */

public class VerftyPhoneBean {
    private int status;
    private VerftyPhoneData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public VerftyPhoneData getData() {
        return data;
    }

    public void setData(VerftyPhoneData data) {
        this.data = data;
    }

    public class VerftyPhoneData{
        private boolean valid;

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }
    }
}
