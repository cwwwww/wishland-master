package com.wishland.www.wanhaohui2.bean;

import java.util.List;

/**
 * Created by admin on 2017/10/31.
 */

public class OfflinePayBean {

    /**
     * status : 200
     * data : [{"title":"公司入款","type":"bank","para":{"limit":"10","tip1":"01 请选择以下公司账号进行转账汇款","tip2":"02 填写汇款单","tip":["一、在金额转出之后请务必填写该页下方的汇款信息表格，以便财务系统能够及时的为您确认并添加金额到您的会员帐户中。","二、本公司最低存款金额为10元，公司财务系统将对银行存款的会员按实际存款金额实行返利派送。","三、跨行转帐请您使用跨行快汇。"],"type":["银行柜台","ATM现金","ATM卡转","网银转账","其它[手动输入]"],"bank":[{"bank":"包商银行【何文魁】","cardid":"50000000000051721901","name":"何文魁","address":"乌兰察布分行广场支行"}]}},{"title":"支付宝入款","type":"alipay","para":{"limit":"10","tip1":"01 请选择以下公司账号进行转账汇款","tip2":"02 填写汇款单","tip":["1、请每次支付宝入款前，查看转账提交页面的最新入款账号，以免误入已停用帐号而造成您资金损失，如存入过期账户，我司则无法受理您的业务。","2、在资金转出之后请务必填写该页下方的转账信息表格，并且备注上转账人姓名，转账方式以便财务系统能够及时的为您确认并添加金额到您的会员账户中。"],"account":[{"picName":"6214835918579457","alipayName":"支付宝转账银行卡【招商银行-张洪军】","alipayID":"6214835918579457","memo":"请认真核对转账对象、名称。","type":"comment"}]}}]
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
         * title : 公司入款
         * type : bank
         * para : {"limit":"10","tip1":"01 请选择以下公司账号进行转账汇款","tip2":"02 填写汇款单","tip":["一、在金额转出之后请务必填写该页下方的汇款信息表格，以便财务系统能够及时的为您确认并添加金额到您的会员帐户中。","二、本公司最低存款金额为10元，公司财务系统将对银行存款的会员按实际存款金额实行返利派送。","三、跨行转帐请您使用跨行快汇。"],"type":["银行柜台","ATM现金","ATM卡转","网银转账","其它[手动输入]"],"bank":[{"bank":"包商银行【何文魁】","cardid":"50000000000051721901","name":"何文魁","address":"乌兰察布分行广场支行"}]}
         */

        private String title;
        private String type;
        private ParaBean para;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ParaBean getPara() {
            return para;
        }

        public void setPara(ParaBean para) {
            this.para = para;
        }

        public static class ParaBean {
            /**
             * limit : 10
             * tip1 : 01 请选择以下公司账号进行转账汇款
             * tip2 : 02 填写汇款单
             * tip : ["一、在金额转出之后请务必填写该页下方的汇款信息表格，以便财务系统能够及时的为您确认并添加金额到您的会员帐户中。","二、本公司最低存款金额为10元，公司财务系统将对银行存款的会员按实际存款金额实行返利派送。","三、跨行转帐请您使用跨行快汇。"]
             * type : ["银行柜台","ATM现金","ATM卡转","网银转账","其它[手动输入]"]
             * bank : [{"bank":"包商银行【何文魁】","cardid":"50000000000051721901","name":"何文魁","address":"乌兰察布分行广场支行"}]
             */

            private String limit;
            private String tip1;
            private String tip2;
            private List<String> tip;
            private List<String> type;
            private List<BankBean> bank;
            private List<AccountBean> account;
            private String img;

            public List<AccountBean> getAccount() {
                return account;
            }

            public void setAccount(List<AccountBean> account) {
                this.account = account;
            }

            public String getLimit() {
                return limit;
            }

            public void setLimit(String limit) {
                this.limit = limit;
            }

            public String getTip1() {
                return tip1;
            }

            public void setTip1(String tip1) {
                this.tip1 = tip1;
            }

            public String getTip2() {
                return tip2;
            }

            public void setTip2(String tip2) {
                this.tip2 = tip2;
            }

            public List<String> getTip() {
                return tip;
            }

            public void setTip(List<String> tip) {
                this.tip = tip;
            }

            public List<String> getType() {
                return type;
            }

            public void setType(List<String> type) {
                this.type = type;
            }

            public List<BankBean> getBank() {
                return bank;
            }

            public void setBank(List<BankBean> bank) {
                this.bank = bank;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public static class BankBean {
                /**
                 * bank : 包商银行【何文魁】
                 * cardid : 50000000000051721901
                 * name : 何文魁
                 * address : 乌兰察布分行广场支行
                 */

                private String bank;
                private String cardid;
                private String name;
                private String address;
                private String img;

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getBank() {
                    return bank;
                }

                public void setBank(String bank) {
                    this.bank = bank;
                }

                public String getCardid() {
                    return cardid;
                }

                public void setCardid(String cardid) {
                    this.cardid = cardid;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }
            }

            public static class AccountBean {

                /**
                 * picName : 6214835918579457
                 * alipayName : 支付宝转账银行卡【招商银行-张洪军】
                 * alipayID : 6214835918579457
                 * memo : 请认真核对转账对象、名称。
                 * type : comment
                 */

                private String picName;
                private String alipayName;
                private String alipayID;
                private String memo;
                private String type;
                private String img;

                public String getPicName() {
                    return picName;
                }

                public void setPicName(String picName) {
                    this.picName = picName;
                }

                public String getAlipayName() {
                    return alipayName;
                }

                public void setAlipayName(String alipayName) {
                    this.alipayName = alipayName;
                }

                public String getAlipayID() {
                    return alipayID;
                }

                public void setAlipayID(String alipayID) {
                    this.alipayID = alipayID;
                }

                public String getMemo() {
                    return memo;
                }

                public void setMemo(String memo) {
                    this.memo = memo;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }
            }
        }
    }
}
