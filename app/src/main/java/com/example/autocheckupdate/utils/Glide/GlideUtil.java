
package com.example.autocheckupdate.utils.Glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.autocheckupdate.MyApplication;
import com.example.autocheckupdate.R;
import com.example.autocheckupdate.utils.Glide.GlideConfig.GlideApp;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Created by 夜听海雨 on 2018/7/15.
 */

public class GlideUtil {
    //如果有需要对图片进行预加载则可以先这样
    // GlideApp.with(MyApplication.context).load("").preload();


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

                   /*     .override(Target.SIZE_ORIGINAL)*/         //加载图片的原始尺寸或者传入 宽高值加载指定的宽高  （200,200）
                       /* .transform(new CropCircleTransformation())*///圆形图片
                        .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(url)
                .apply(options)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(view);

    }


}
