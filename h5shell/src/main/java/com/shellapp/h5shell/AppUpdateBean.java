package com.shellapp.h5shell;

/**
 * Created by admin on 2017/10/22.
 */

public class AppUpdateBean {


    /**
     * appname : xpj1.0
     * vsersionCode : 102
     * versionName : 1.0.2
     * versionType : 3
     * serverVersion : http://38.27.96.183/appUpdate.json
     * updateurl : http://38.27.96.183/xpj.apk
     * upgradeinfo : xpj version control
     */

    private String appname;
    private String vsersionCode;
    private String versionName;
    private int versionType;
    private String serverVersion;
    private String updateurl;
    private String upgradeinfo;

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getVsersionCode() {
        return vsersionCode;
    }

    public void setVsersionCode(String vsersionCode) {
        this.vsersionCode = vsersionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionType() {
        return versionType;
    }

    public void setVersionType(int versionType) {
        this.versionType = versionType;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    public String getUpdateurl() {
        return updateurl;
    }

    public void setUpdateurl(String updateurl) {
        this.updateurl = updateurl;
    }

    public String getUpgradeinfo() {
        return upgradeinfo;
    }

    public void setUpgradeinfo(String upgradeinfo) {
        this.upgradeinfo = upgradeinfo;
    }
}
