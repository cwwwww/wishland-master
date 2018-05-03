package com.wishland.www.wanhaohui2.bean;

/**
 * Created by admin on 2017/10/24.
 */

public class LevelBean {
    private int status;
    private LevelData data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LevelData getData() {
        return data;
    }

    public void setData(LevelData data) {
        this.data = data;
    }

    public class LevelData{
        private String username;
        private AmountData amount;
        private LevelInfo level;
        private NextLevelData nextlevel;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public LevelInfo getLevel() {
            return level;
        }

        public void setLevel(LevelInfo level) {
            this.level = level;
        }

        public AmountData getAmount() {
            return amount;
        }

        public void setAmount(AmountData amount) {
            this.amount = amount;
        }

        public NextLevelData getNextlevel() {
            return nextlevel;
        }

        public void setNextlevel(NextLevelData nextlevel) {
            this.nextlevel = nextlevel;
        }

        public class AmountData{
            private String ckje;
            private String ztzje;

            public String getCkje() {
                return ckje;
            }

            public void setCkje(String ckje) {
                this.ckje = ckje;
            }

            public String getZtzje() {
                return ztzje;
            }

            public void setZtzje(String ztzje) {
                this.ztzje = ztzje;
            }
        }

        public class LevelInfo{
            private String levelname;
            private String upgradetype;
            private String levelimg;
            private String amount;
            private String dmamount;

            public String getLevelname() {
                return levelname;
            }

            public void setLevelname(String levelname) {
                this.levelname = levelname;
            }

            public String getUpgradetype() {
                return upgradetype;
            }

            public void setUpgradetype(String upgradetype) {
                this.upgradetype = upgradetype;
            }

            public String getLevelimg() {
                return levelimg;
            }

            public void setLevelimg(String levelimg) {
                this.levelimg = levelimg;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getDmamount() {
                return dmamount;
            }

            public void setDmamount(String dmamount) {
                this.dmamount = dmamount;
            }
        }

        public class NextLevelData{
            private String levelid;
            private String levelname;
            private String upgradetype;
            private String levelimg;
            private String amount;
            private String dmamount;
            private double ckje_need;
            private double ztzje_need;
            private String ztzje;
            private String ckje;

            public String getLevelid() {
                return levelid;
            }

            public void setLevelid(String levelid) {
                this.levelid = levelid;
            }

            public String getZtzje() {
                return ztzje;
            }

            public void setZtzje(String ztzje) {
                this.ztzje = ztzje;
            }

            public String getCkje() {
                return ckje;
            }

            public void setCkje(String ckje) {
                this.ckje = ckje;
            }

            public String getLevelname() {
                return levelname;
            }

            public void setLevelname(String levelname) {
                this.levelname = levelname;
            }

            public String getUpgradetype() {
                return upgradetype;
            }

            public void setUpgradetype(String upgradetype) {
                this.upgradetype = upgradetype;
            }

            public String getLevelimg() {
                return levelimg;
            }

            public void setLevelimg(String levelimg) {
                this.levelimg = levelimg;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getDmamount() {
                return dmamount;
            }

            public void setDmamount(String dmamount) {
                this.dmamount = dmamount;
            }


            public double getCkje_need() {
                return ckje_need;
            }

            public void setCkje_need(double ckje_need) {
                this.ckje_need = ckje_need;
            }

            public double getZtzje_need() {
                return ztzje_need;
            }

            public void setZtzje_need(double ztzje_need) {
                this.ztzje_need = ztzje_need;
            }

            public NextLevelData(String levelid, String levelname, String upgradetype, String levelimg, String amount, String dmamount, double ckje_need, double ztzje_need, String ztzje, String ckje) {
                this.levelid = levelid;
                this.levelname = levelname;
                this.upgradetype = upgradetype;
                this.levelimg = levelimg;
                this.amount = amount;
                this.dmamount = dmamount;
                this.ckje_need = ckje_need;
                this.ztzje_need = ztzje_need;
                this.ztzje = ztzje;
                this.ckje = ckje;
            }
        }

        
    }
}
