package com.wishland.www.wanhaohui2.bean;

/**
 * Created by admin on 2017/10/26.
 */

public class WalletBean {
    private int status;
    private WalletData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public WalletData getData() {
        return data;
    }

    public void setData(WalletData data) {
        this.data = data;
    }

    public class WalletData{
        private String type;
        private String balance;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }
    }
}
