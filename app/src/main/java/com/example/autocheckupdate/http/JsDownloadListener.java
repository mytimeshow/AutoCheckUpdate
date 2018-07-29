package com.example.autocheckupdate.http;

/**
 * Created by 夜听海雨 on 2018/7/29.
 */

public interface JsDownloadListener {
    void onStartDownload();

    void onProgress(int progress);

    void onFinishDownload();

    void onFail(String errorInfo);


}
