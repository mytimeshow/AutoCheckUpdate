package com.example.autocheckupdate.base;

import android.app.Activity;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autocheckupdate.R;
import com.example.autocheckupdate.module.dailog.LoadingDialog;
import com.example.autocheckupdate.utils.AppManagerUtil;
import com.example.autocheckupdate.utils.Logger;
import com.gyf.barlibrary.ImmersionBar;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by 夜听海雨 on 2018/7/7.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView{

    public Toolbar mToolbar;
    private TextView toolbarRightText;
    private ImageView toolbarRightImage;
    public TextView toolbarTitleText;
    private ImageView toolbarLeftImage;
    protected View statusBarView;
    public ImmersionBar mImmersionBar;
    private LinearLayout llToolbar;

    private OnToolBarClickListener onClickListenerLeft;
    private OnToolBarClickListener onClickListenerRight;

    public static BaseActivity baseActivity;
    private LoadingDialog mLoadingDialog;
    private volatile int requestLoadingDialogTimes = 0;
    private Toast mToast;
    private Disposable disposable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        AppManagerUtil.getInstance().addActivity(this);

        mImmersionBar= ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.barColor).init();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarRightText = (TextView) findViewById(R.id.toolbar_right_text);
        toolbarRightImage = (ImageView) findViewById(R.id.toolbar_right_image);
        toolbarTitleText = (TextView) findViewById(R.id.toolbar_title);
        toolbarLeftImage = (ImageView) findViewById(R.id.toolbar_left_image);
        statusBarView  = findViewById(R.id.bar_status_view);
        llToolbar = findViewById(R.id.ll_toolbar);


        if (mToolbar != null) {

            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
          // setToolBarLeftIcon(R.mipmap.ic_back);
            if (toolbarLeftImage!=null){
                toolbarLeftImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                       hideSoftInput(view.getWindowToken());

                        if (onClickListenerLeft != null) {
                            onClickListenerLeft.onClick();
                        }

                            finish();


                    }
                });
            }
            if (toolbarRightImage!=null){
                toolbarRightImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       hideSoftInput(view.getWindowToken());
                        if (onClickListenerRight != null) {
                            onClickListenerRight.onClick();
                        }
                    }
                });
            }
            if (toolbarRightText!=null){
                toolbarRightText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       hideSoftInput(view.getWindowToken());
                        if (onClickListenerRight != null) {
                            onClickListenerRight.onClick();
                        }
                    }
                });
            }

        }

        initView();

            initData();


    }
    protected abstract int setLayout();
    protected abstract void initView();
    protected abstract void initData() ;

    @Override
    public void showLoadingDialog() {

        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.newInstance();
            Logger.d("showLoadingDialog####","  create");
        }
        if (requestLoadingDialogTimes == 0) {
            mLoadingDialog.show(getSupportFragmentManager(), "LoadingDialog");
            Logger.d("showLoadingDialog####","  show");
        }
        requestLoadingDialogTimes++;
    }

    @Override
    public void dismissLoadingDialog() {
        requestLoadingDialogTimes--;
        if (requestLoadingDialogTimes <= 0 && mLoadingDialog != null&&mLoadingDialog.getFragmentManager()!=null) {
            requestLoadingDialogTimes = 0;
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
            Logger.d("dismissLoading","Dialog####");
        }
    }

    @Override
    public void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       if(mImmersionBar!=null) mImmersionBar.destroy();
        if(disposable!=null) disposable.dispose();
        AppManagerUtil.getInstance().finishActivity(this);
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void showError(Throwable e) {

        if (e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
            showToast(getString(R.string.network_exception));
        } /*else if (e instanceof UserOAuth.LoginExpiredException) {
            showToast(getString(R.string.login_expired_exception));
        }*/ else {
            showToast(getString(R.string.server_exception));
        }
        e.printStackTrace();
    }
    public static abstract class MyObserver<T> implements Observer<T> {

        @Override
        public void onSubscribe(Disposable d) {
            Activity activity=AppManagerUtil.getInstance().getTopActivity();
               baseActivity= (BaseActivity) activity;
               baseActivity.showLoadingDialog();

            Logger.d(d.getClass().getSimpleName(),"had susbcrite");
        }

        @Override
        public void onNext(T o) {

        }

        @Override
        public void onError(Throwable e) {
            baseActivity.showError(e);
            baseActivity.dismissLoadingDialog();
            Logger.d("onError",e.getMessage().toString());

        }

        @Override
        public void onComplete() {
            baseActivity.dismissLoadingDialog();
            Logger.d("onComplete"," onsubscribe complete");
        }
    }
    /**
     * 自定义接口
     */
    public interface OnToolBarClickListener {
        void onClick();
    }
}
