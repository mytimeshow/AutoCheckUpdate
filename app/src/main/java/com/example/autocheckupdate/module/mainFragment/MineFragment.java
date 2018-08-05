package com.example.autocheckupdate.module.mainFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.autocheckupdate.R;
import com.example.autocheckupdate.base.BaseFragment;

/**
 * Created by 夜听海雨 on 2018/8/4.
 */

public class MineFragment extends BaseFragment {
    private TextView tvMineFragment;



    @Override
    public int getLayout() {
        return R.layout.mine_fragment;
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        tvMineFragment = (TextView) rootView.findViewById(R.id.tv_news_fragment);
    }
}
