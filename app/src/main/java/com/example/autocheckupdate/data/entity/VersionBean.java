package com.example.autocheckupdate.data.entity;

import com.example.autocheckupdate.data.Response;

/**
 * Created by 夜听海雨 on 2018/7/8.
 */

public class VersionBean{
   private int version;
    private String versionName;
    private  String apkUrl;
    private String message;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
