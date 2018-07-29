package com.example.autocheckupdate.http;

import com.example.autocheckupdate.constance.Constance;
import com.example.autocheckupdate.utils.HttpsUtils;
import com.example.autocheckupdate.utils.JsonParseUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 夜听海雨 on 2018/7/7.
 */

public class NetUse {
    private OkHttpClient client=new OkHttpClient.Builder()
            .sslSocketFactory(HttpsUtils.initSSLSocketFactory(),HttpsUtils.initTrustManager())
            .connectTimeout(50, TimeUnit.SECONDS)
           // .addInterceptor(new CommomParamsIntercept())
            .writeTimeout(50,TimeUnit.SECONDS)
            .readTimeout(50,TimeUnit.SECONDS)
            .followRedirects(true)
            .hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            })
            .build();
    private Retrofit retrofit;
    private  NetMethod netMethod;
    private Observable<ResponseBody> observable;
    //"apikey=MVOFWSm6npLKitJscKNI6uItrkkPUpDJ0sJc5WBKM5DXQ6s3gvqXxCHY5kVpCuXC";
    private static volatile NetUse mNetUse;
    public static NetUse getInstanse(){
        if(mNetUse==null){
            synchronized (NetUse.class){
                if(mNetUse==null){
                    mNetUse=new NetUse();
                }
            }
        }
        return mNetUse;
    }
    private NetUse(){
        retrofit=new Retrofit.Builder()
                .client(client)
                .baseUrl(Constance.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        netMethod=retrofit.create(NetMethod.class);


    }
    //无参get请求
    public  Observable<ResponseBody> get(String url){
        return netMethod.get(url).observeOn(Schedulers.newThread())
                ;
    }
    //带参get请求
    public  Observable<ResponseBody> get(String url, Map<String,Object> query){
        return netMethod.get(url,query).subscribeOn(Schedulers.newThread() );
    }
    //带参和heade rget请求
    public  Observable<ResponseBody> get(String url, Map<String,Object> query,Map<String,Object> headers){
        return netMethod.get(url,query,headers).subscribeOn(Schedulers.newThread() );
    }
    //无参post请求
    public  Observable<ResponseBody> post(String url){
        return netMethod.post(url).subscribeOn(Schedulers.newThread() );
    }
    //待参post请求
    public  Observable<ResponseBody> post(String url, Map<String,Object> query){
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), JsonParseUtil.toJson(query));
        return netMethod.post(url,body).subscribeOn(Schedulers.newThread() );
    }
    //带参和header post请求
    public  Observable<ResponseBody> post(String url, Map<String,Object> query,Map<String,Object> headers){
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), JsonParseUtil.toJson(query));
        return netMethod.post(url,body,headers).subscribeOn(Schedulers.newThread() );
    }
    //单文件上传
    public Observable<ResponseBody> upLoad(String url,Map<String,Object> param,String fileUrl){
      /*  "..\\Autocheckupdate\\src\\main\\resources\\girl.jpg"*/
        File file=new File(fileUrl);
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/octet-stream"),file);
        MultipartBody.Part photo= MultipartBody.Part.createFormData("photo",file.getName(),requestBody);
        return netMethod.upLoad(url,photo,param);
    }
    //多文件上传
    public  Observable<ResponseBody> upLoadMoreFile(String url,Map<String,Object> param,String[] urlList){
        int size=urlList.length;
        Map<String,RequestBody> fileMap=new HashMap<>();
        File file=null;
        RequestBody requestBody=null;
        for(int i=0;i<size;i++){
            file=new File(urlList[i]);
            requestBody=RequestBody.create(MediaType.parse("application/octet-stream"),file);
            fileMap.put("photos\"; filename=\"" + file.getName(),requestBody);
        }
        return netMethod.upLoadMoreFile(url,fileMap,param);
    }
    //文件下载
    public Observable<ResponseBody> downLoad(String url){
        return netMethod.downLaod(url).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
    }

    static class CommomParamsIntercept implements Interceptor {
        JsDownloadListener jsDownloadListener;
        public CommomParamsIntercept(JsDownloadListener jsDownloadListener){
            this.jsDownloadListener=jsDownloadListener;
        }
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                   // .addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Accept", "*/*")
                    .addHeader("Cookie", "add cookies here")
                    .build();
            Response response=chain.proceed(request);
            return response.newBuilder().body(new BaseResponse(response.body(),jsDownloadListener)).build();
        }
    }

}
