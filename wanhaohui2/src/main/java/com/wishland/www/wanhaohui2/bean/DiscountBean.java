package com.wishland.www.wanhaohui2.bean;

import java.util.List;

/**
 * Created by admin on 2017/10/12.
 */

public class DiscountBean {

    /**
     * status : 200
     * data : [{"imgUrl":"https://www.whh7788.com/m/data/templates/v.3/images/activity/hd_img023.jpg","title":"VIP8您挑礼品 我买单","start":"10月24日至10月30日","end":"","url":"https://www.whh7788.com/api/pages/activity.php?id=0","content":""},{"imgUrl":"https://www.whh7788.com/m/data/templates/v.3/images/activity/hd_img01.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"https://www.whh7788.com/api/pages/activity.php?id=1","content":""},{"imgUrl":"https://www.whh7788.com/m/data/templates/v.3/images/activity/hd_img02.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"https://www.whh7788.com/api/pages/activity.php?id=2","content":""},{"imgUrl":"https://www.whh7788.com/m/data/templates/v.3/images/activity/hd_img03.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"https://www.whh7788.com/api/pages/activity.php?id=3","content":""}]
     */

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

    public static class DataBean {

        /**
         * id : 0
         * aid : 3
         * state : apply
         * game : 真人视讯
         * imgUrl : https://whh4488.com/api/images/activity/hd_img023.png
         * imgUrlShow : https://whh4488.com/api/images/activity/show/hd_img023.png
         * title : VIP周周奖
         * start : 敬请期待
         * end : 人人享受VIP待遇
         * url : https://whh4488.com/api/pages/activity.php?id=0&aid=3&state=apply
         * content :
         */

        private int id;
        private String aid;
        private String state;
        private String game;
        private String imgUrl;
        private String imgUrlShow;
        private String title;
        private String start;
        private String end;
        private String url;
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getGame() {
            return game;
        }

        public void setGame(String game) {
            this.game = game;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getImgUrlShow() {
            return imgUrlShow;
        }

        public void setImgUrlShow(String imgUrlShow) {
            this.imgUrlShow = imgUrlShow;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
