package com.example.autocheckupdate.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by user on 2018/8/3.
 */

public class MyIntercept implements Interceptor {
    private static ProgressListener listener;
    public static void setListener(ProgressListener listeners){
        listener=listeners;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request=chain.request()
                 .newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "*/*")
                .addHeader("Cookie", "add cookies here")
                .build();
        Response response=chain.proceed(request);
        ResponseBody responseBody=response.body();


        return response.newBuilder().body(new ProgressResponseBody(responseBody,listener)).build();
    }
}
