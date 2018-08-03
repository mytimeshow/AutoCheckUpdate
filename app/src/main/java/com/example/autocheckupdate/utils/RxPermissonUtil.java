package com.example.autocheckupdate.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.example.autocheckupdate.MainActivity;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by 夜听海雨 on 2018/7/29.
 */

public class RxPermissonUtil {
    private static final String TAG = "RxPermissonUtil";

    /**同时请求多个权限（分别获取结果）的情况*/
    private void MultPermission2(Activity context) {
        RxPermissions rxPermissions = new RxPermissions(context); // where this is an Activity instance
        rxPermissions.requestEach(Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE)//权限名称，多个权限之间逗号分隔开
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        Log.e(TAG, "{accept}permission.name=" + permission.name);
                        Log.e(TAG, "{accept}permission.granted=" + permission.granted);
                        if (permission.name.equals(Manifest.permission.READ_PHONE_STATE) && permission.granted) {
                            // 已经获取权限
                            Toast.makeText(context, "已经获取权限", Toast.LENGTH_SHORT).show();
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();//根据不同的手机设备返回IMEI，MEID或者ESN码
                            Toast.makeText(context, "{accept}deviceId=" + deviceId, Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
    public static boolean checkPermisson(Activity activity,String permisson){
        if (ActivityCompat.checkSelfPermission(activity, permisson) != PackageManager.PERMISSION_GRANTED) {

            return false;
        }
        return true;

    }
    /**只有一个运行时权限申请的情况*/
    public static void requestPermission(Activity activity, String permisson,Consumer<Permission> consumer){
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(permisson)
                .subscribe(new Consumer<Permission>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            consumer.accept(permission);

                           // Toast.makeText(activity,"用户已经同意该权限",Toast.LENGTH_LONG).show();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框

                            Toast.makeText(activity,"用户拒绝了该权限",Toast.LENGTH_LONG).show();
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』，提醒用户手动打开权限

                            Toast.makeText(activity,"权限被拒绝，请在设置里面开启相应权限，若无相应权限会影响使用",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
