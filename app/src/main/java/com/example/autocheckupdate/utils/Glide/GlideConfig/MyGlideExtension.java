package com.example.autocheckupdate.utils.Glide.GlideConfig;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by 夜听海雨 on 2018/8/3.
 */
@GlideExtension
public class MyGlideExtension {
    private MyGlideExtension() {

    }

    @GlideOption
    public static void cacheSource(RequestOptions options) {
        options.diskCacheStrategy(DiskCacheStrategy.DATA);
    }
    @GlideOption
    public static void cacheMemory(RequestOptions options) {
        options.skipMemoryCache(false);
    }

}
