package com.wishland.www.wanhaohui2.bean;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;

/**
 * Created by admin on 2017/10/13.
 */

public class MyDiscountBean {
    private int status;
    private MyDiscountData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MyDiscountData getData() {
        return data;
    }

    public void setData(MyDiscountData data) {
        this.data = data;
    }

    public class MyDiscountData{

        private List<NewDiscountBean> discount;
        private List<NewDiscountBean> apply;
        private List<NewDiscountBean> reject;
        private List<NewDiscountBean> invalid;
        private List<NewDiscountBean> pass;

        public List<NewDiscountBean> getPass() {
            return pass;
        }

        public void setPass(List<NewDiscountBean> pass) {
            this.pass = pass;
        }

        public List<NewDiscountBean> getDiscount() {
            return discount;
        }

        public void setDiscount(List<NewDiscountBean> discount) {
            this.discount = discount;
        }

        public List<NewDiscountBean> getApply() {
            return apply;
        }

        public void setApply(List<NewDiscountBean> apply) {
            this.apply = apply;
        }

        public List<NewDiscountBean> getReject() {
            return reject;
        }

        public void setReject(List<NewDiscountBean> reject) {
            this.reject = reject;
        }

        public List<NewDiscountBean> getInvalid() {
            return invalid;
        }

        public void setInvalid(List<NewDiscountBean> invalid) {
            this.invalid = invalid;
        }

        public class NewDiscountBean{
            private String id;
            private String title;
            private String victitle;
            private String starttime;
            private String endtime;
            private String dm;
            private String game;
            private String type;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getVictitle() {
                return victitle;
            }

            public void setVictitle(String victitle) {
                this.victitle = victitle;
            }

            public String getStarttime() {
                return starttime;
            }

            public void setStarttime(String starttime) {
                this.starttime = starttime;
            }

            public String getEndtime() {
                return endtime;
            }

            public void setEndtime(String endtime) {
                this.endtime = endtime;
            }

            public String getDm() {
                return dm;
            }

            public void setDm(String dm) {
                this.dm = dm;
            }

            public String getGame() {
                return game;
            }

            public void setGame(String game) {
                this.game = game;
            }
        }
    }
}
