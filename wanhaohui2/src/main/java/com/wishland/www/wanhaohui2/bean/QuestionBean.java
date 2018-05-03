package com.wishland.www.wanhaohui2.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/11/8.
 */

public class QuestionBean {
    private int status;
    private String errorMsg;
    private List<QuestionData> data;

    @Override
    public String toString() {
        return "QuestionBean{" +
                "status=" + status +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<QuestionData> getData() {
        return data;
    }

    public void setData(List<QuestionData> data) {
        this.data = data;
    }

    public class QuestionData  {

        private String title;
        private List<ContentBean> content;

        @Override
        public String toString() {
            return "QuestionData{" +
                    "title='" + title + '\'' +
                    ", content=" + content +
                    '}';
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public class ContentBean{
            private String title;
            private String content;
            private boolean isSee=false;

            public boolean isSee() {
                return isSee;
            }

            public void setSee(boolean see) {
                isSee = see;
            }

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

            @Override
            public String toString() {
                return "ContentBean{" +
                        "title='" + title + '\'' +
                        ", content='" + content + '\'' +
                        ", isSee=" + isSee +
                        '}';
            }
        }
    }
}
