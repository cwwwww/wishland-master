package com.wishland.www.wanhaohui2.bean;

import java.util.List;

/**
 * Created by admin on 2017/12/3.
 */

public class HotGameBean {

    /**
     * status : 200
     * data : [{"fid":"20064","category":"zr","plat":"ag","subtype":"","code":"ag","para":"{\"name\":\"AGu56fdu9645u9986\",\"img\":\"https://[host]/api/images/game/ag.png\",\"url\":\"https://[host]/api/url_agent.php?token=[token]&url=..%2Fagline%2Findex.php\",\"img_m\":\"https://[host]/api/images/game/ag.png\",\"url_m\":\"https://[host]/api/url_agent.php?token=[token]&url=..%2Fagline%2Findex.php\"}"},{"fid":"20071","category":"zr","plat":"dg","subtype":"","code":"dg","para":"{\"name\":\"DGu89c6u8bafu6e38u620f\",\"img\":\"https://[host]/api/images/game/dg.png\",\"url\":\"https://[host]/api/url_agent.php?token=[token]&url=..%2Fdgline%2Findex.php\",\"img_m\":\"https://[host]/api/images/game/dg.png\",\"url_m\":\"https://[host]/api/url_agent.php?token=[token]&url=..%2Fdgline%2Findex.php\"}"},{"fid":"20072","category":"ty","plat":"jst_sp","subtype":"","code":"jst_sp","para":"{\"name\":\"u6c99u5df4u4f53u80b2\",\"img\":\"https://[host]/api/images/game/jst_sp.png\",\"url\":\"https://[host]/api/url_agent.php?token=[token]&url=jst_spline%2Findex.php\",\"img_m\":\"\",\"url_m\":\"\"}"},{"fid":"20073","category":"ty","plat":"ssty","subtype":"","code":"ssty","para":"{\"name\":\"u7687u51a0u4f53u80b2\",\"img\":\"https://[host]/api/images/game/ssty.png\",\"url\":\"https://[host]/api/url_agent.php?token=[token]&url=sstyline%2Findex.php\",\"img_m\":\"https://[host]/api/images/game/ssty.png\",\"url_m\":\"https://[host]/api/url_agent.php?token=[token]&url=sstyline%2Findex.php\"}"},{"fid":"20195","category":"dz","plat":"AGMG","subtype":"","code":"101","para":"{\"name\":\"u6c34u679cu62c9u9738\",\"img\":\"https://[host]/mgindex/images/AGMG/FRU_ZH.jpg\",\"url\":\"https://[host]/api/url_agent.php?token=[token]&url=agline%2Findex.php%3Fslot_type%3D101\",\"img_m\":\"https://[host]/mgindex/images/AGMG/FRU_ZH.jpg\",\"url_m\":\"https://[host]/api/url_agent.php?token=[token]&url=agline%2Findex.php%3Fslot_type%3D101\"}"}]
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
         * fid : 20064
         * category : zr
         * plat : ag
         * subtype :
         * code : ag
         * para : {"name":"AGu56fdu9645u9986","img":"https://[host]/api/images/game/ag.png","url":"https://[host]/api/url_agent.php?token=[token]&url=..%2Fagline%2Findex.php","img_m":"https://[host]/api/images/game/ag.png","url_m":"https://[host]/api/url_agent.php?token=[token]&url=..%2Fagline%2Findex.php"}
         */

        private String fid;
        private String category;
        private String plat;
        private String subtype;
        private String code;
        private String para;
        private boolean favorite;
        private boolean trymode;

        public boolean isTrymode() {
            return trymode;
        }

        public void setTrymode(boolean trymode) {
            this.trymode = trymode;
        }

        public String getFid() {
            return fid;
        }

        public void setFid(String fid) {
            this.fid = fid;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPlat() {
            return plat;
        }

        public void setPlat(String plat) {
            this.plat = plat;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPara() {
            return para;
        }

        public void setPara(String para) {
            this.para = para;
        }

        public boolean isFavorite() {
            return favorite;
        }

        public void setFavorite(boolean favorite) {
            this.favorite = favorite;
        }
    }
}
