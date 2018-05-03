package com.wishland.www.wanhaohui2.bean;

import java.util.List;

/**
 * Created by admin on 2017/10/26.
 */
//支付宝和微信
public class PayWayBean {

    /**
     * status : 200
     * data : [{"pay_name":"9：网银QQ1%","risk":"1","item":{"name":"快捷网银","code":[{"img":"https://47.91.249.217/api/images/bank/unionpay.png","name":"银联","code":"31"}]},"url":"https://47.91.249.217/api/url_agent.php?token=[token]&url=pay&version=3&id=150&money=[money]&code=[code]"},{"pay_name":"11：网银京东QQ1%","risk":"1","item":{"name":"网银","code":[{"img":"https://47.91.249.217/api/images/bank/unionpay.png","name":"银联","code":"1005WAP网银"}]},"url":"https://47.91.249.217/api/url_agent.php?token=[token]&url=pay&version=3&id=157&money=[money]&code=[code]"}]
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
         * pay_name : 9：网银QQ1%
         * risk : 1
         * item : {"name":"快捷网银","code":[{"img":"https://47.91.249.217/api/images/bank/unionpay.png","name":"银联","code":"31"}]}
         * url : https://47.91.249.217/api/url_agent.php?token=[token]&url=pay&version=3&id=150&money=[money]&code=[code]
         */

        private String min;
        private String max;
        private String pay_name;
        private String risk;
        private ItemBean item;
        private String url;

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getPay_name() {
            return pay_name;
        }

        public void setPay_name(String pay_name) {
            this.pay_name = pay_name;
        }

        public String getRisk() {
            return risk;
        }

        public void setRisk(String risk) {
            this.risk = risk;
        }

        public ItemBean getItem() {
            return item;
        }

        public void setItem(ItemBean item) {
            this.item = item;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public static class ItemBean {
            /**
             * name : 快捷网银
             * code : [{"img":"https://47.91.249.217/api/images/bank/unionpay.png","name":"银联","code":"31"}]
             */

            private String name;
            private List<CodeBean> code;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<CodeBean> getCode() {
                return code;
            }

            public void setCode(List<CodeBean> code) {
                this.code = code;
            }

            public static class CodeBean {
                /**
                 * img : https://47.91.249.217/api/images/bank/unionpay.png
                 * name : 银联
                 * code : 31
                 */

                private String img;
                private String name;
                private String code;

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }
            }
        }
    }
}
