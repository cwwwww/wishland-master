package com.wishland.www.wanhaohui2.bean;

/**
 * Created by admin on 2017/12/4.
 */

public class LoginAdvertiseBean {
    private int status;
    private AdvertiseData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public AdvertiseData getData() {
        return data;
    }

    public void setData(AdvertiseData data) {
        this.data = data;
    }

    public class AdvertiseData{
        private String img;
        private String url;

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
    }
}
