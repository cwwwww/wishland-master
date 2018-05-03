package com.wishland.www.wanhaohui2.bean;

import java.util.List;

/**
 * Created by admin on 2017/10/15.
 */

public class MemberRatingBean {

    private int status;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    /*
     {
            "levelname":"VIP1",
            "upgradetype":"所有达标",
            "levelimg":"1.gif",
            "amount":"88.00",
            "dmamount":"88.00",
            "limit":"总有效投注额>=1000.00|总存款额>=100.00"
        },
     */

    public class DataBean {

        private String levelname;
        private String upgradetype;
        private String levelimg;
        private String amount;
        private String dmamount;
        private String limit;
        private String ztzje;
        private String ckje;

        public DataBean(String levelname, String upgradetype, String levelimg, String amount, String dmamount, String limit, String ztzje, String ckje) {
            this.levelname = levelname;
            this.upgradetype = upgradetype;
            this.levelimg = levelimg;
            this.amount = amount;
            this.dmamount = dmamount;
            this.limit = limit;
            this.ztzje = ztzje;
            this.ckje = ckje;
        }

        public String getZtzje() {
            return ztzje;
        }

        public void setZtzje(String ztzje) {
            this.ztzje = ztzje;
        }

        public String getCkje() {
            return ckje;
        }

        public void setCkje(String ckje) {
            this.ckje = ckje;
        }

        public String getLevelname() {
            return levelname;
        }

        public void setLevelname(String levelname) {
            this.levelname = levelname;
        }

        public String getUpgradetype() {
            return upgradetype;
        }

        public void setUpgradetype(String upgradetype) {
            this.upgradetype = upgradetype;
        }

        public String getLevelimg() {
            return levelimg;
        }

        public void setLevelimg(String levelimg) {
            this.levelimg = levelimg;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getDmamount() {
            return dmamount;
        }

        public void setDmamount(String dmamount) {
            this.dmamount = dmamount;
        }

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }
    }
}
