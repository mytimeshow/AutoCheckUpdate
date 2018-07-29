package com.example.autocheckupdate.module.camera;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;

/**
 * Created by 夜听海雨 on 2018/7/28.
 */

public class MyCameraManager {
    private Activity activity;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyCameraManager(Activity activity) throws CameraAccessException {
        this.activity = activity;
        CameraManager manager = (CameraManager) activity.getSystemService(Context.CAMERA_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


        }
        manager.openCamera("1001", new CameraStateCallBack(), null);

    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    class CameraStateCallBack extends CameraDevice.StateCallback{

        @Override
        public void onOpened(@NonNull CameraDevice cameraDevice) {

        }

        @Override
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {

        }

        @Override
        public void onError(@NonNull CameraDevice cameraDevice, int i) {

        }
    }

}
