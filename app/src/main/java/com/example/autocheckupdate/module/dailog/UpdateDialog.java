package com.example.autocheckupdate.module.dailog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.autocheckupdate.R;
import com.example.autocheckupdate.base.BaseDialogFragment;
import com.example.autocheckupdate.utils.CheckVersionUpdateUtil;
import com.example.autocheckupdate.utils.RichText;

/**
 * Created by 夜听海雨 on 2018/7/8.
 */

public class UpdateDialog extends BaseDialogFragment{



    private TextView tv_content;
    private Button btn_update,btn_ignore;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(rootView==null){
            rootView=inflater.inflate(R.layout.updata_dailog,container);
            btn_ignore=rootView.findViewById(R.id.btn_ignore);
            btn_update=rootView.findViewById(R.id.btn_update);
            tv_content=rootView.findViewById(R.id.tv_content);
            initView();
        }

        return rootView;
    }

    private void initView() {
        tv_content.setText(RichText.newRichText().append("修复了一些线上bug")
        .append("\n")
         .append("2.0")
        .build());
        btn_ignore.setOnClickListener(v ->
                closeDailog()
        );
        btn_update.setOnClickListener(v->
                updata()
        );

    }

    private void updata() {
        CheckVersionUpdateUtil.getInstance().setContext(getActivity()).initService();
        this.dismiss();
    }

    private void closeDailog(){
        if(getDialog().isShowing())getDialog().dismiss();
    }
    public static UpdateDialog getUpdateDialog(){
        return new UpdateDialog();
    }
}
