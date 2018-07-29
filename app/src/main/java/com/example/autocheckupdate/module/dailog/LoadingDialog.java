package com.example.autocheckupdate.module.dailog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.autocheckupdate.R;
import com.example.autocheckupdate.base.BaseDialogFragment;


/**
 * Created by 夜听海雨 on 2017/9/18.
 * 加载框
 */

public class LoadingDialog extends BaseDialogFragment {

    ImageView iv_loading;

    public static LoadingDialog newInstance() {
        return new LoadingDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.dialog_loading, container);
          iv_loading=rootView.findViewById(R.id.iv_loading);
            initView();
        }
        return rootView;
    }

    @Override
    protected void initWindow(Window window) {
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private void initView() {
        iv_loading.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_loading));
    }
}
