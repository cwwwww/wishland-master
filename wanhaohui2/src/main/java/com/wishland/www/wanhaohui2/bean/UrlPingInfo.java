package com.wishland.www.wanhaohui2.bean;

import android.os.Parcel;

/**
 * Created by cjay on 2016/12/2.
 */

public class UrlPingInfo implements android.os.Parcelable {

    public long PingValue;
    public String URLString;
    public String URLTitle;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.PingValue);
        dest.writeString(this.URLString);
        dest.writeString(this.URLTitle);
    }

    public UrlPingInfo() {
    }

    protected UrlPingInfo(Parcel in) {
        this.PingValue = in.readLong();
        this.URLString = in.readString();
        this.URLTitle = in.readString();
    }

    public static final Creator<UrlPingInfo> CREATOR = new Creator<UrlPingInfo>() {
        @Override
        public UrlPingInfo createFromParcel(Parcel source) {
            return new UrlPingInfo(source);
        }

        @Override
        public UrlPingInfo[] newArray(int size) {
            return new UrlPingInfo[size];
        }
    };


    @Override
    public String toString() {
        return URLTitle + "-" + PingValue;
    }

}
