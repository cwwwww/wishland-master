package com.wishland.www.wanhaohui2.bean;

import java.util.List;

/**
 * Created by admin on 2017/11/1.
 */

public class PlaceOrderListBean {
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

    public class DataBean{
        private int total;
        private List<PlaceOrderData> bet;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<PlaceOrderData> getBet() {
            return bet;
        }

        public void setBet(List<PlaceOrderData> bet) {
            this.bet = bet;
        }

        public class PlaceOrderData{
            private double bet;
            private double win;
            private String bettime;

            public double getBet() {
                return bet;
            }

            public void setBet(double bet) {
                this.bet = bet;
            }

            public double getWin() {
                return win;
            }

            public void setWin(double win) {
                this.win = win;
            }

            public String getBettime() {
                return bettime;
            }

            public void setBettime(String bettime) {
                this.bettime = bettime;
            }
        }
    }
}
