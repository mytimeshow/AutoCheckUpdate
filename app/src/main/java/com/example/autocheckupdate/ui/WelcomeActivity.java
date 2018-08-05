package com.example.autocheckupdate.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.autocheckupdate.MainActivity;
import com.example.autocheckupdate.R;

public class WelcomeActivity extends AppCompatActivity {
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                    if(msg.what==0){
                        startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                        finish();
                    }
                super.handleMessage(msg);
            }
        };
        handler.sendEmptyMessageDelayed(0,1500);
    }
}
