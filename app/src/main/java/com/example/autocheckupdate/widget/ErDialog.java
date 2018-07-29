package com.example.autocheckupdate.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.autocheckupdate.R;
import com.example.autocheckupdate.zxing.encoding.EncodingHandler;
import com.google.zxing.WriterException;


/**
 * Created by sming on 2017/8/4.
 */

public class ErDialog extends Dialog implements View.OnClickListener {
    private Context context;
    String content;

    public ErDialog(Context context, String content) {
        // 更改样式,把背景设置为透明的
        super(context, R.style.Dialog);
        this.context = context;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //加载dialog的布局
        setContentView(R.layout.dialog_er_layout);
        //拿到布局控件进行处理
        ImageView qrcodeImg = (ImageView) findViewById(R.id.iv_view);
        TextView tv_order_id = findViewById(R.id.tv_order_id);
        if (!TextUtils.isEmpty(content)) {
            if (content.contains("g+")){
//                content = content.substring(2,content.length());
                tv_order_id.setText("取酒码:g"+content.substring(2,content.length()));
            }else {
                tv_order_id.setText("酒局号:"+content);
            }


            Bitmap www = null;
            try {
                www = EncodingHandler.createQRCode(content, 500);
                qrcodeImg.setImageBitmap(www);
            } catch (WriterException e) {
                e.printStackTrace();
            }

        }
        qrcodeImg.setOnClickListener(this);
        //初始化布局的位置
        initLayoutParams();
    }

    // 初始化布局的参数
    private void initLayoutParams() {
        // 布局的参数
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER;
        params.alpha = 1f;
        getWindow().setAttributes(params);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}