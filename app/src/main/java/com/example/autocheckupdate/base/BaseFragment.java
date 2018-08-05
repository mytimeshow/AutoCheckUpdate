package com.example.autocheckupdate.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by 夜听海雨 on 2018/8/4.
 */

public  abstract class BaseFragment extends Fragment{
    protected View rootView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            rootView=inflater.inflate(getLayout(),container,false);
            initViews();
            initData();
        return rootView;
    }

    protected abstract void initData();

    protected abstract void initViews();
    public void showToast(String msg){
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

    }

    public abstract int getLayout();
}
