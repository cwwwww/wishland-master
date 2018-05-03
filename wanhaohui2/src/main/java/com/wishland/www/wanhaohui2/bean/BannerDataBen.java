package com.wishland.www.wanhaohui2.bean;

import java.util.List;

/**
 * Created by admin on 2017/10/15.
 */

public class BannerDataBen {

    /**
     * status : 200
     * data : [{"title":"","img":"https://www.whh7788.com/m/./data/templates/v.3/images/banner/1.jpg","url":"https://www.whh7788.com/api/url_agent.php?token=[token]&url=http%3A%2F%2Fviphd.whhfamily.com"},{"title":"","img":"https://www.whh7788.com/m/./data/templates/v.3/images/banner/16.jpg","url":""},{"title":"","img":"https://www.whh7788.com/m/./data/templates/v.3/images/banner/18.jpg","url":"https://www.whh7788.com/api/url_agent.php?token=[token]&url=agline%2Findex.php%3Fslot_type%3D105%3B"},{"title":"","img":"https://www.whh7788.com/m/./data/templates/v.3/images/banner/17.jpg","url":"https://www.whh7788.com/api/url_agent.php?token=[token]&url=http%3A%2F%2Fwww.sjb118.com%2F"},{"title":"","img":"https://www.whh7788.com/m/./data/templates/v.3/images/banner/7.jpg","url":"https://www.whh7788.com/api/url_agent.php?token=[token]&url=hccpline%2Findex.php%3Flottery%3D26"}]
     * token : b4y681d1D4y9Z9d638h563z7K6A5U2T0k4Q7r5B565q6r9i467s0q824s6l0u5d53541f0p8t3s6k6r2r155t8n644t8
     */

    private int status;
    private String token;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        /**
         * title :
         * img : https://47.91.249.217/m/./data/templates/v.3/images/banner/1.jpg
         * url : https://47.91.249.217/api/url_agent.php?token=[token]&url=http%3A%2F%2Fqhb.whh89.com%2Fcontroller_wap%2Fplay_game_controller.php
         * weburl : http://qhb.whh89.com/controller_wap/play_game_controller.php
         * way :
         */

        private String title;
        private String img;
        private String url;
        private String weburl;
        private String way;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getWeburl() {
            return weburl;
        }

        public void setWeburl(String weburl) {
            this.weburl = weburl;
        }

        public String getWay() {
            return way;
        }

        public void setWay(String way) {
            this.way = way;
        }
    }
}
