package com.wishland.www.wanhaohui2.bean;

import java.util.List;

/**
 * Created by admin on 2017/12/12.
 */

public class AboutUsBean2 {

    /**
     * status : 200
     * data : {"img":"https://47.91.249.217/api/images/site/certificate.jpg","url":"http://169.56.143.117:6889/index.php/index/about.html","about":[{"title":"","content":"这个是前面的第一段介绍"},{"title":"诚信为本","content":"诚信为本诚信为本诚信为本诚信为本诚信为本"},{"title":"多元化游戏","content":"多元化游戏多元化游戏多元化游戏多元化游戏"},{"title":"账户与资金","content":"账户与资金账户与资金账户与资金账户与资金账户与资金"}]}
     */

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

    public static class DataBean {
        /**
         * img : https://47.91.249.217/api/images/site/certificate.jpg
         * url : http://169.56.143.117:6889/index.php/index/about.html
         * about : [{"title":"","content":"这个是前面的第一段介绍"},{"title":"诚信为本","content":"诚信为本诚信为本诚信为本诚信为本诚信为本"},{"title":"多元化游戏","content":"多元化游戏多元化游戏多元化游戏多元化游戏"},{"title":"账户与资金","content":"账户与资金账户与资金账户与资金账户与资金账户与资金"}]
         */

        private String img;
        private String url;
        private List<AboutBean> about;

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

        public List<AboutBean> getAbout() {
            return about;
        }

        public void setAbout(List<AboutBean> about) {
            this.about = about;
        }

        public static class AboutBean {
            /**
             * title :
             * content : 这个是前面的第一段介绍
             */

            private String title;
            private String content;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
