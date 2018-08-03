package com.example.autocheckupdate.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.example.autocheckupdate.R;
import com.example.autocheckupdate.http.NetUse;
import com.example.autocheckupdate.utils.AndroidUtil;
import com.example.autocheckupdate.utils.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observer;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

/**
 * Created by 夜听海雨 on 2018/7/7.
 */

public class DownLaodService extends Service{
    private static final String TAG = "DownLaodService";

    //定义notify的id，避免与其它的notification的处理冲突
    private static final int NOTIFY_ID = 2;
    private static final String CHANNEL = "update";
    private static final int DOWNLOAD_PROGRESS=0;
    private static final int DOWNLOAD_ERROR=1;
    private static final int DOWNLOAD_COMPLETE=2;
    private OnCompleteListenner listenner;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    private Notification notification;
    private ServiceConnection connection;

    private Mybinder mybinder=new Mybinder();

    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what){
                case 0:
                    int progress= (int) message.obj;
                    builder
                            .setContentTitle("正在下载新版本")
                            .setProgress(100,progress,false)
                            .setContentText(progress+"%")
                            .setAutoCancel(true)
                            .setWhen(System.currentTimeMillis());
                     notification=builder.build();
                    notification.flags=Notification.FLAG_AUTO_CANCEL;
                    notificationManager.notify(NOTIFY_ID, notification);
                    Logger.d(TAG,"progress "+progress);
                    break;
                case 1:
                notificationManager.cancel(NOTIFY_ID);
                    Logger.d(TAG,"error");
              //  stopSelf();
                    break;
                case 2:
                    notificationManager.cancel(NOTIFY_ID);
                    if(AndroidUtil.isOnFront()){
                        notificationManager.cancel(NOTIFY_ID);
                        Logger.d(TAG,"isOnFront");
                    }else {
                        onDownLoadFinished(message);
                    }
//                        if(connection!=null){
//                            unbindService(connection);
//                        }

                    break;
            }

            return false;
        }
    });
    /**
     * 安装
     * 7.0 以上记得配置 fileProvider
     */
    private Intent installIntent(String path){
        try {
            File file = new File(path);
            String authority = getApplicationContext().getPackageName() + ".fileProvider";
            Uri fileUri = FileProvider.getUriForFile(getApplicationContext(), authority, file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            }
            return intent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mybinder;
    }
    private void setBuilder(int progress){
        notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
         builder =new NotificationCompat.Builder(DownLaodService.this);
         builder.setAutoCancel(true)
                .setContentTitle("正在下载")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_foreground))
                .setWhen(System.currentTimeMillis())
                 .setOngoing(true);
            notification=builder.build();
        notificationManager.notify(NOTIFY_ID, notification);
    }
    /*下载*/
    public void downLoadApk() {
        String url="blob/80e353488e947797d6184d147bcac7ccc2f081b1/app-release.apk";
        String path="QQ_884.apk";
        NetUse.getInstanse().downLoad(path).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {
                Logger.d(TAG+"onSubscribe",d.getClass().getSimpleName());
            }
            @Override
            public void onNext(ResponseBody responseBody) {
                    Logger.d(TAG,"responseBody length is  "+responseBody.contentLength());
               new Thread(() -> initNotifiationProgress(responseBody)).start();
               // initNotifiationProgress(responseBody);
            }

            @Override
            public void onError(Throwable e) {
                Message message=Message.obtain();
                message.what=1;
               // handler.sendMessage(message);
                Logger.d(TAG+"onError",e.toString());
            }

            @Override
            public void onComplete() {

                Logger.d(TAG+"onComplete","download success");
            }
        });

    }
    public void onDownLoadFinished(Message message){
        notificationManager.cancel(NOTIFY_ID);
        Logger.d(TAG,"PendingIntent");
        Intent intent=installIntent((String)message.obj);
        //startActivity(intent);

        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,intent,0
               /* PendingIntent.FLAG_UPDATE_CURRENT*/);
        builder.setContentTitle(getPackageName().substring(getPackageName().lastIndexOf(".")+1))
                .setProgress(100,100,false)
                .setContentText("点击安装")
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis());
        notification = builder.build();
           notification.flags=Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(NOTIFY_ID, notification);
        stopSelf();
        //  listenner.onFinished();
        //stopSelf();

    }

    private void initNotifiationProgress(ResponseBody responseBody) {
        if(responseBody==null){
            Message message=Message.obtain();
            message.what=1;
            message.obj="下载失败";
            handler.sendMessage(message);
        }
        setBuilder(0);
        File file=new File(Environment.getExternalStorageDirectory().getPath()+"/"+"app.apk");
        if(file.exists()){
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //  return;
        }
        InputStream is=null;
        FileOutputStream fos=null;
        int len=0;
        try {
            Logger.e(TAG,"now thread is "+ Thread.currentThread().getName());

            fos=new FileOutputStream(file);
            is=responseBody.byteStream();
            long length=responseBody.contentLength();
            Logger.d(TAG,"length    "+length);
            int sum=0;
            int progress=0;
            byte[] bytes=new byte[2048];
            while ((len=is.read(bytes))!=-1){
                fos.write(bytes,0,len);
                sum+=len;

                if((int) (sum*1.0f*100/length)-progress==1){
                    progress= (int) (sum*1.0f*100/length);
                    Message message=Message.obtain();
                    message.what=0;
                    message.obj=progress;
                    handler.sendMessage(message);
                }

            }
            Message message=Message.obtain();
            message.what=2;
            message.obj=file.getAbsolutePath();
            handler.sendMessage(message);

            //   Logger.d(TAG+"onnext" ,"finished");
        }catch (Exception e){
            Logger.d(TAG,e.getMessage().toString());
        }finally {
            try {
                if(is!=null){
                    is.close();
                }
                if(fos!=null){
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    public void setListenner(OnCompleteListenner listenner){
            this.listenner=listenner;
    }
    @Override
    public void onDestroy() {
        Logger.d(TAG,"ondestory");
        super.onDestroy();
        Logger.d(TAG,"ondestory");
    }
    public void showToash(){
        Toast.makeText(DownLaodService.this,"service is still alive",Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onUnbind(Intent intent) {
        Logger.d(TAG,"onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        notificationManager.cancelAll();
        notificationManager=null;
        builder=null;
        Logger.d(TAG,"unbindService");
    }

    public class Mybinder extends Binder{

        public DownLaodService getDownLaodService(){
            return DownLaodService.this;
                    }
        public void setServiceConection(ServiceConnection conection){
            DownLaodService.this.connection=conection;
        }
    }

        public interface OnCompleteListenner{
            void onFinished();
        }

}
