package com.example.autocheckupdate.api;

import com.example.autocheckupdate.data.Response;
import com.example.autocheckupdate.http.NetUse;
import com.example.autocheckupdate.utils.CheckVersionUpdateUtil;
import com.example.autocheckupdate.utils.JsonParseUtil;

import java.util.HashMap;
import java.util.Map;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 夜听海雨 on 2018/7/8.
 */

public class checkVersionUpdataApi {

    public static Observable getVersionInfo(){
        Map<String,Object> param=new HashMap<>();
        return NetUse.getInstanse().get("",param)
                    .map(s-> (Response<CheckVersionUpdateUtil>) JsonParseUtil.fromJson(s.toString(), new JsonParseUtil.Type(Response.class,CheckVersionUpdateUtil.class)))
                    .subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread());
    }
}
