package com.wishland.www.wanhaohui2.bean;

import java.util.List;

/**
 * Created by admin on 2017/10/26.
 */

public class PayWayTypeBean {

    /**
     * status : 200
     * data : {"pay_list":[{"title":"微信在线","title_img":"https://www.whh7788.com/api.n/images/pay/wx_wap.png","type":"weixinwap"},{"title":"支付宝在线","title_img":"https://www.whh7788.com/api.n/images/pay/alipay_wap.png","type":"alipaywap"},{"title":"财付通在线","title_img":"https://www.whh7788.com/api.n/images/pay/qq_wap.png","type":"caifutongxwap"},{"title":"网银在线","title_img":"https://www.whh7788.com/api.n/images/pay/wy_wap.png","type":"wangyinwap"},{"title":"公司入款","title_img":"https://www.whh7788.com/api.n/images/pay/bank_zz.png","type":"hk"},{"title":"支付宝入款","title_img":"https://www.whh7788.com/api.n/images/pay/alipay_zz.png","type":"alipay"}],"url":"https://www.whh7788.com/api.n/index.php?pay/para&type="}
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
         * pay_list : [{"title":"微信在线","title_img":"https://www.whh7788.com/api.n/images/pay/wx_wap.png","type":"weixinwap"},{"title":"支付宝在线","title_img":"https://www.whh7788.com/api.n/images/pay/alipay_wap.png","type":"alipaywap"},{"title":"财付通在线","title_img":"https://www.whh7788.com/api.n/images/pay/qq_wap.png","type":"caifutongxwap"},{"title":"网银在线","title_img":"https://www.whh7788.com/api.n/images/pay/wy_wap.png","type":"wangyinwap"},{"title":"公司入款","title_img":"https://www.whh7788.com/api.n/images/pay/bank_zz.png","type":"hk"},{"title":"支付宝入款","title_img":"https://www.whh7788.com/api.n/images/pay/alipay_zz.png","type":"alipay"}]
         * url : https://www.whh7788.com/api.n/index.php?pay/para&type=
         */

        private String url;
        private List<PayListBean> pay_list;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<PayListBean> getPay_list() {
            return pay_list;
        }

        public void setPay_list(List<PayListBean> pay_list) {
            this.pay_list = pay_list;
        }

        public static class PayListBean {
            /**
             * title : 微信在线
             * title_img : https://www.whh7788.com/api.n/images/pay/wx_wap.png
             * type : weixinwap
             */

            private String title;
            private String title_img;
            private String type;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTitle_img() {
                return title_img;
            }

            public void setTitle_img(String title_img) {
                this.title_img = title_img;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
