package com.example.autocheckupdate;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.example.autocheckupdate.base.BaseActivity;
import com.example.autocheckupdate.module.camera.TakePictureActivity;
import com.example.autocheckupdate.utils.CheckVersionUpdateUtil;
import com.example.autocheckupdate.utils.Logger;
import com.example.autocheckupdate.utils.RxPermissonUtil;
import com.example.autocheckupdate.zxing.activity.CaptureActivity;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Url;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, Consumer<Boolean> {
    private static final String TAG = "MainActivity";
    private RadioGroup mRadioGroup;
    private ViewPager mViewPager;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mRadioGroup = findViewById(R.id.rg_01);
        mViewPager = findViewById(R.id.viewPager_main);
        mRadioGroup.setOnCheckedChangeListener(this);
        toolbarTitleText.setText("title");

    }

    //@SuppressLint("MissingPermission")
    //  @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void initData() {













    }
   
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1002 && resultCode==RESULT_OK){
            Bundle bundle=data.getExtras();

            showToast(bundle.getString("result"));
        }




        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_scan:
                Intent intent1=new Intent(this, CaptureActivity.class);
                startActivityForResult(intent1,1002);
                break;
            case R.id.menu_picture:
                Intent intent=new Intent(this, TakePictureActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_one:
                CheckVersionUpdateUtil.getInstance().setContext(this).checkVersion();
                break;
            case R.id.menu_two:

                break;

        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            if(keyCode==KeyEvent.KEYCODE_BACK){
                moveTaskToBack(true);
                return true;
            }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_01:
                mRadioGroup.check(R.id.rb_01);
                    break;
                case R.id.rb_02:
                    mRadioGroup.check(R.id.rb_02);
                    break;
                case R.id.rb_03:
                    mRadioGroup.check(R.id.rb_03);
                    break;
            }
    }

    @Override
    public void accept(Boolean aBoolean) throws Exception {
        if(aBoolean){
            showToast("permisson granted");
        }else {
            showToast("permisson denied");
        }
    }
}
