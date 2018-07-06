package com.shellapp.h5shell;

/**
 * Created by admin on 2017/10/22.
 */

public class AppUpdateBean {


    /**
     * appName : xpj1.0
     * versionCode : 102
     * versionName : 1.0.2
     * versionType : 3
     * serverVersion : http://38.27.96.183/appUpdate.json
     * updateUrl : http://38.27.96.183/xpj.apk
     * upgradeInfo : xpj version control
     */

    private String appName;
    private String versionCode;
    private String versionName;
    private int versionType;
    private String serverVersion;
    private String updateUrl;
    private String upgradeInfo;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
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

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public String getUpgradeInfo() {
        return upgradeInfo;
    }

    public void setUpgradeInfo(String upgradeInfo) {
        this.upgradeInfo = upgradeInfo;
    }
}
