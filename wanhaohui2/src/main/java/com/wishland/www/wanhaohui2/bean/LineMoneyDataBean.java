package com.wishland.www.wanhaohui2.bean;

import java.util.List;

/**
 * Created by admin on 2017/10/16.
 */

public class LineMoneyDataBean {
    private int status;
    private LineMoneyData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LineMoneyData getData() {
        return data;
    }

    public void setData(LineMoneyData data) {
        this.data = data;
    }

    public class LineMoneyData {
        private double total;
        private List<WalletBean> wallet;
        private CategoryBean category;

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public List<WalletBean> getWallet() {
            return wallet;
        }

        public void setWallet(List<WalletBean> wallet) {
            this.wallet = wallet;
        }

        public CategoryBean getCategory() {
            return category;
        }

        public void setCategory(CategoryBean category) {
            this.category = category;
        }

        public class WalletBean{
            private String name;
            private String amout;
            private String wallettype;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAmout() {
                return amout;
            }

            public void setAmout(String amout) {
                this.amout = amout;
            }

            public String getWallettype() {
                return wallettype;
            }

            public void setWallettype(String wallettype) {
                this.wallettype = wallettype;
            }

            @Override
            public String toString() {
                return "WalletBean{" +
                        "name='" + name + '\'' +
                        ", amout='" + amout + '\'' +
                        ", wallettype='" + wallettype + '\'' +
                        '}';
            }
        }

        public class CategoryBean{
           private List<String> 真人视讯;
           private List<String> 电子游戏;
           private List<String> 彩票游戏;
           private List<String> 体育娱乐;

            public List<String> get真人视讯() {
                return 真人视讯;
            }

            public void set真人视讯(List<String> 真人视讯) {
                this.真人视讯 = 真人视讯;
            }

            public List<String> get电子游戏() {
                return 电子游戏;
            }

            public void set电子游戏(List<String> 电子游戏) {
                this.电子游戏 = 电子游戏;
            }

            public List<String> get彩票游戏() {
                return 彩票游戏;
            }

            public void set彩票游戏(List<String> 彩票游戏) {
                this.彩票游戏 = 彩票游戏;
            }

            public List<String> get体育娱乐() {
                return 体育娱乐;
            }

            public void set体育娱乐(List<String> 体育娱乐) {
                this.体育娱乐 = 体育娱乐;
            }
        }
    }
}
