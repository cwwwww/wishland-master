package com.wishland.www.wanhaohui2.bean;

import java.util.List;

/**
 * Created by admin on 2017/10/28.
 */

public class QuestDealBean {
    private int status;
    private QuestDealData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public QuestDealData getData() {
        return data;
    }

    public void setData(QuestDealData data) {
        this.data = data;
    }

    public class QuestDealData{
        private String type;
        private List<OtherBean> otherList;
        private List<CommonBean> commonList;
        private int total;
        private int queryid;
        private int queryCnt;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<OtherBean> getOtherList() {
            return otherList;
        }

        public void setOtherList(List<OtherBean> otherList) {
            this.otherList = otherList;
        }

        public List<CommonBean> getCommonList() {
            return commonList;
        }

        public void setCommonList(List<CommonBean> commonList) {
            this.commonList = commonList;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getQueryid() {
            return queryid;
        }

        public void setQueryid(int queryid) {
            this.queryid = queryid;
        }

        public int getQueryCnt() {
            return queryCnt;
        }

        public void setQueryCnt(int queryCnt) {
            this.queryCnt = queryCnt;
        }

        public class OtherBean{
            private String serialNum;
            private String usaTime;
            private String chinaTime;
            private String sum;
            private String type;
            private String notes;
            private String state;

            public String getSerialNum() {
                return serialNum;
            }

            public void setSerialNum(String serialNum) {
                this.serialNum = serialNum;
            }

            public String getUsaTime() {
                return usaTime;
            }

            public void setUsaTime(String usaTime) {
                this.usaTime = usaTime;
            }

            public String getChinaTime() {
                return chinaTime;
            }

            public void setChinaTime(String chinaTime) {
                this.chinaTime = chinaTime;
            }

            public String getSum() {
                return sum;
            }

            public void setSum(String sum) {
                this.sum = sum;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }
        }
        public class CommonBean{
            private String serialNum;
            private String usaTime;
            private String chinaTime;
            private String sum;
            private String type;
            private String points;
            private String state;

            public String getSerialNum() {
                return serialNum;
            }

            public void setSerialNum(String serialNum) {
                this.serialNum = serialNum;
            }

            public String getUsaTime() {
                return usaTime;
            }

            public void setUsaTime(String usaTime) {
                this.usaTime = usaTime;
            }

            public String getChinaTime() {
                return chinaTime;
            }

            public void setChinaTime(String chinaTime) {
                this.chinaTime = chinaTime;
            }

            public String getSum() {
                return sum;
            }

            public void setSum(String sum) {
                this.sum = sum;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            @Override
            public String toString() {
                return "CommonBean{" +
                        "serialNum='" + serialNum + '\'' +
                        ", usaTime='" + usaTime + '\'' +
                        ", chinaTime='" + chinaTime + '\'' +
                        ", sum='" + sum + '\'' +
                        ", type='" + type + '\'' +
                        ", points='" + points + '\'' +
                        ", state='" + state + '\'' +
                        '}';
            }
        }
    }
}
