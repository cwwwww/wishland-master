package com.wishland.www.wanhaohui2.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 2017/12/12.
 */

public class QuestionBean2 {

    /**
     * status : 200
     * data : {"0":{"title":"游戏投注","content":[{"title":"游戏投注","content":"游戏投注啊 游戏投注"},{"title":"技术问题","content":"技术问题啊 技术问题"},{"title":"常见问题","content":"常见问题啊 常见问题"}]},"1":{"title":"技术问题","content":[{"title":"游戏投注","content":"游戏投注啊 游戏投注"},{"title":"技术问题","content":"技术问题啊 技术问题"},{"title":"常见问题","content":"常见问题啊 常见问题"}]},"2":{"title":"常见问题","content":[{"title":"游戏投注","content":"游戏投注啊 游戏投注"},{"title":"技术问题","content":"技术问题啊 技术问题"},{"title":"常见问题","content":"常见问题啊 常见问题"}]},"url":"http://169.56.143.117:6889/index.php/index/faq.html"}
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
         * 0 : {"title":"游戏投注","content":[{"title":"游戏投注","content":"游戏投注啊 游戏投注"},{"title":"技术问题","content":"技术问题啊 技术问题"},{"title":"常见问题","content":"常见问题啊 常见问题"}]}
         * 1 : {"title":"技术问题","content":[{"title":"游戏投注","content":"游戏投注啊 游戏投注"},{"title":"技术问题","content":"技术问题啊 技术问题"},{"title":"常见问题","content":"常见问题啊 常见问题"}]}
         * 2 : {"title":"常见问题","content":[{"title":"游戏投注","content":"游戏投注啊 游戏投注"},{"title":"技术问题","content":"技术问题啊 技术问题"},{"title":"常见问题","content":"常见问题啊 常见问题"}]}
         * url : http://169.56.143.117:6889/index.php/index/faq.html
         */

        @SerializedName("0")
        private _$0Bean _$0;
        @SerializedName("1")
        private _$1Bean _$1;
        @SerializedName("2")
        private _$2Bean _$2;
        private String url;

        public _$0Bean get_$0() {
            return _$0;
        }

        public void set_$0(_$0Bean _$0) {
            this._$0 = _$0;
        }

        public _$1Bean get_$1() {
            return _$1;
        }

        public void set_$1(_$1Bean _$1) {
            this._$1 = _$1;
        }

        public _$2Bean get_$2() {
            return _$2;
        }

        public void set_$2(_$2Bean _$2) {
            this._$2 = _$2;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public static class _$0Bean {
            /**
             * title : 游戏投注
             * content : [{"title":"游戏投注","content":"游戏投注啊 游戏投注"},{"title":"技术问题","content":"技术问题啊 技术问题"},{"title":"常见问题","content":"常见问题啊 常见问题"}]
             */

            private String title;
            private List<ContentBean> content;

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

            public static class ContentBean {
                /**
                 * title : 游戏投注
                 * content : 游戏投注啊 游戏投注
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

        public static class _$1Bean {
            /**
             * title : 技术问题
             * content : [{"title":"游戏投注","content":"游戏投注啊 游戏投注"},{"title":"技术问题","content":"技术问题啊 技术问题"},{"title":"常见问题","content":"常见问题啊 常见问题"}]
             */

            private String title;
            private List<ContentBeanX> content;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<ContentBeanX> getContent() {
                return content;
            }

            public void setContent(List<ContentBeanX> content) {
                this.content = content;
            }

            public static class ContentBeanX {
                /**
                 * title : 游戏投注
                 * content : 游戏投注啊 游戏投注
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

        public static class _$2Bean {
            /**
             * title : 常见问题
             * content : [{"title":"游戏投注","content":"游戏投注啊 游戏投注"},{"title":"技术问题","content":"技术问题啊 技术问题"},{"title":"常见问题","content":"常见问题啊 常见问题"}]
             */

            private String title;
            private List<ContentBeanXX> content;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<ContentBeanXX> getContent() {
                return content;
            }

            public void setContent(List<ContentBeanXX> content) {
                this.content = content;
            }

            public static class ContentBeanXX {
                /**
                 * title : 游戏投注
                 * content : 游戏投注啊 游戏投注
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
}
