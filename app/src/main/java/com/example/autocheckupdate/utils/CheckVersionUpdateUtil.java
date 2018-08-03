package com.example.autocheckupdate.utils;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;

import com.example.autocheckupdate.MainActivity;
import com.example.autocheckupdate.module.dailog.UpdateDialog;
import com.example.autocheckupdate.service.DownLaodService;

/**
 * Created by 夜听海雨 on 2018/7/8.
 */

public class CheckVersionUpdateUtil {
    private  final String TAG = "CheckVersionUpdateUtil";
    private  ServiceConnection conn;
    private  DownLaodService downLaodService;
    private Intent intent;
    private  Context context;
    private static CheckVersionUpdateUtil mInstance;
    private int remoteVersion;
    public CheckVersionUpdateUtil setContext( Context context){
        this.context=context;
        return this;
    }
    public CheckVersionUpdateUtil(){};

    public static CheckVersionUpdateUtil getInstance(){
        if(mInstance==null){
            synchronized (CheckVersionUpdateUtil.class){
                if (mInstance==null){
                    mInstance=new CheckVersionUpdateUtil();
                }
            }
        }
        return mInstance;
    }

  public DownLaodService getDownLaodService(){
        return downLaodService;
  }
  public Intent getIntent(){
      return intent;
  }
  public ServiceConnection getConn(){
      return conn;
  }

    public  void  initService(){
        conn=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                  DownLaodService.Mybinder   mybinder= (DownLaodService.Mybinder) iBinder;
                downLaodService=mybinder.getDownLaodService();
                mybinder.setServiceConection(conn);
                downLaodService.downLoadApk();

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
         intent=new Intent(context,DownLaodService.class);
        context.bindService(intent,conn, Service.BIND_AUTO_CREATE);
    }
    //检查当前版本与服务器的版本，不同则更新
    public boolean checkVersion() {
        PackageManager packageManager=context.getPackageManager();
        try {
            PackageInfo packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            String versionName=packageInfo.versionName;
            int verssionCode=packageInfo.versionCode;
            if(getRemoteVersion()>verssionCode){
                showUpdateDailog();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }
    //显示更新的弹窗
    private void showUpdateDailog() {
        MainActivity activity= (MainActivity) context;
        UpdateDialog.getUpdateDialog().show(activity.getSupportFragmentManager(),"updateDailog");
    }

    private int getRemoteVersion(){
            remoteVersion=3;
       /* Map<String,Object> param=new HashMap<>();
        NetUse.getInstanse()
                .get("url",param)
                .map(s->(Response<VersionBean>)JsonParseUtil.fromJson(s.toString(),new JsonParseUtil.Type(Response.class, VersionBean.class)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<VersionBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<VersionBean> versionBeanResponse) {
                        remoteVersion =versionBeanResponse.getData().getVersion();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });*/
        return remoteVersion;
    }
}
