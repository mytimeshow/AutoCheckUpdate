package com.example.autocheckupdate.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.autocheckupdate.R;
import com.example.autocheckupdate.utils.Glide.DisplayUtil;
import com.example.autocheckupdate.utils.Logger;

/**
 * Created by 夜听海雨 on 2018/8/5.
 */

public class ChooseTabView extends View{
    private static final String TAG = "ChooseTabView";
    String[] texts={"0","1","2","3","4","5","6","7","不限"};
    float radius; //圆半径
    float diameter;//圆直径
    int defaultCircleColor=getResources().getColor(R.color.text_light_gray);
    int defaultTextColor=getResources().getColor(R.color.text_light_gray);
    int selectedCircleColor=getResources().getColor(R.color.bg_main_button_red_normal);
    int selectedTextColor=getResources().getColor(R.color.bg_main_button_red_normal);
    float textSize;
    int selectedPosition;
    Paint paintCircle;
    Paint paintText;




    public ChooseTabView(Context context) {
        super(context);
    }

    public ChooseTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.ChooseTabView);
        selectedCircleColor=a.getColor(R.styleable.ChooseTabView_selectedCircleColor,Color.GREEN);
        selectedTextColor=a.getColor(R.styleable.ChooseTabView_selectedTextColor,defaultTextColor);
        textSize=a.getDimension(R.styleable.ChooseTabView_textSize,30);
        //defaultRadius=a.getDimension(R.styleable.ChooseTabView_CircleRdius,10f);
        a.recycle();
        initPaint(context);

    }

    private void initPaint(Context context) {
        paintCircle =new Paint();
        paintCircle.setColor(defaultCircleColor);
        paintCircle.setAntiAlias(true);
        paintCircle.setStyle(Paint.Style.FILL);

        paintText =new Paint();
        paintText.setColor(Color.BLACK);
        paintText.setTextSize(textSize);
        paintText.setAntiAlias(true);
        paintText.setStyle(Paint.Style.STROKE);
    }

    public ChooseTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.ChooseTabView);
        selectedCircleColor=a.getColor(R.styleable.ChooseTabView_selectedCircleColor,defaultCircleColor);
        selectedTextColor=a.getColor(R.styleable.ChooseTabView_selectedTextColor,defaultTextColor);

        //defaultRadius=a.getDimension(R.styleable.ChooseTabView_CircleRdius,10f);
        a.recycle();
        paintCircle =new Paint();
        paintCircle.setColor(defaultCircleColor);
        paintCircle.setAntiAlias(true);
        paintCircle.setStyle(Paint.Style.FILL);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action=event.getAction();
        if(MotionEvent.ACTION_DOWN==action){
            float x=event.getX();
            selectedPosition= (int) (x/diameter);
            invalidate();
            Logger.e(TAG,"selectedPosition is "+selectedPosition);
            return true;
        }

        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int width;
        int heigth=widthSize/9;
        if(widthMode==MeasureSpec.UNSPECIFIED){
            width=500;
        }
        setMeasuredDimension(widthSize,heigth);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        radius=getMeasuredWidth()/9/2*1.0f;
        diameter=radius*2*1.0f;
        drawCircle(canvas);

    }

    private void drawCircle(Canvas canvas) {

        for(int i=0,length=texts.length;i<length;i++){
                if(i==selectedPosition){
                    if(selectedPosition==length-1) paintText.setTextSize(textSize/4*3);
                    paintCircle.setColor(selectedCircleColor);
                }else if(i==length-1 || i==selectedPosition){
                    paintText.setTextSize(textSize/4*3);
                }
                canvas.drawCircle((float) ((i+0.5)*diameter),radius,radius, paintCircle);
                canvas.drawText(texts[i], (float) ((0.5+i)*diameter)-textSize/3*texts[i].length(), (float) (radius+textSize/3), paintText);
            paintText.setTextSize(textSize);
            paintCircle.setColor(defaultCircleColor);
        }
    }
}
