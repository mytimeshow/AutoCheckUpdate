
package com.example.autocheckupdate.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.autocheckupdate.R;

import java.util.Observer;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;


/**
 * Created by 夜听海雨 on 2018/7/15.
 */

public class GlideUtil {
    private static volatile GlideUtil instance;
    public static GlideUtil getInstance(){
        if(instance==null){
            synchronized (GlideUtil.class){
                if(instance==null){
                    instance=new GlideUtil();
                }
            }
        }
        return instance;
    }
    public void loadImage(Context context, String url, ImageView view){
       // MultiTransformation multiTransformation=new MultiTransformation(new MaskTransformation(1));
        RequestOptions options=new RequestOptions()
                        .centerCrop()
                        .error(R.drawable.timg)
                        .placeholder(R.drawable.timg)
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(view);
    }


}
