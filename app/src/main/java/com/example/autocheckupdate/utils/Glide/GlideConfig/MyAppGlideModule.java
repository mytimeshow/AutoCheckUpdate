package com.example.autocheckupdate.utils.Glide.GlideConfig;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Created by 夜听海雨 on 2018/8/3.
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule{
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {

    }


}
