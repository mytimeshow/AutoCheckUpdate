package com.example.autocheckupdate.module.mainFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.autocheckupdate.R;
import com.example.autocheckupdate.base.BaseFragment;

/**
 * Created by 夜听海雨 on 2018/8/4.
 */

public class AudioFragment extends BaseFragment {
    private TextView tvAudioFragment;



    @Override
    public int getLayout() {
        return R.layout.audio_fragment;
    }
    @Override
    protected void initData() {
        //FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        //NewsFragment fragment= (NewsFragment) fragmentManager.findFragmentByTag("android:switcher:"+R.id.viewPager_main+":"+0);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initViews() {
        tvAudioFragment = (TextView) rootView.findViewById(R.id.tv_news_fragment);
    }

}
