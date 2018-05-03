package com.wishland.www.wanhaohui2.bean;

import java.util.List;

/**
 * Created by admin on 2017/11/1.
 */

public class BetTypeBean {
    private int status;
    private List<BetTypeData> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<BetTypeData> getData() {
        return data;
    }

    public void setData(List<BetTypeData> data) {
        this.data = data;
    }

    public class BetTypeData{
        private String key;
        private String name;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
