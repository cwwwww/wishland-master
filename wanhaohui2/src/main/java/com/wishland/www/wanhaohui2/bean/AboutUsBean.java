package com.wishland.www.wanhaohui2.bean;

import java.util.List;

/**
 * Created by admin on 2017/11/16.
 */

public class AboutUsBean {
    private int status;
    private AboutUsData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public AboutUsData getData() {
        return data;
    }

    public void setData(AboutUsData data) {
        this.data = data;
    }

    public class AboutUsData{
        private String img;
        private List<AboutContent> about;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public List<AboutContent> getAbout() {
            return about;
        }

        public void setAbout(List<AboutContent> about) {
            this.about = about;
        }

        public class AboutContent{
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
