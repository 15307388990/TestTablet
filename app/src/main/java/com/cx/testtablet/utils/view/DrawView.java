package com.cx.testtablet.utils.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.cx.testtablet.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class DrawView extends View {
    private static final int MAX_TOUCHPOINTS = 5;
    private static final String START_TEXT = "请随便触摸屏幕进行测试";
    private Paint mPaint;
    private Paint textPaint = new Paint();
    private Paint mPointPaint = new Paint();
    private Canvas mCanvas;//画布
    private Bitmap bitmap;//位图
    private float x = 0;//点默认X坐标   
    private float y = 0; //点默认Y坐标   
    private List<Float> cx = new ArrayList<Float>();
    private List<Float> cy = new ArrayList<Float>();
    private int mWidth, mHeight;
    private int pointerCount;// 屏幕触点数量
    private int touchFlag = 0;

    public DrawView(Context context) {
        super(context);
        initialize();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
// TODO Auto-generated constructor stub
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pointerCount = event.getPointerCount();
        if (pointerCount > MAX_TOUCHPOINTS) {
            pointerCount = MAX_TOUCHPOINTS;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
//最后一个起来了，用于取消十字
            touchFlag = 1;
            invalidate();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            for (int i = 0; i < pointerCount; i++) {
                x = event.getX(i);
                y = event.getY(i);
                drawLine(cx.get(i), cy.get(i), x, y);
            }
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
//当第一个手指按下时调用，在新的画布上画
            mCanvas = new Canvas();
            bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888); //设置位图的宽高
            mCanvas.setBitmap(bitmap);
            touchFlag = 0;
        }
        for (int i = 0; i < pointerCount; i++) {
//记录 触摸点的坐标，并用于画十字
            cx.set(i, event.getX(i));
            cy.set(i, event.getY(i));
            drawPoint(cx.get(i), cy.get(i));
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
// TODO Auto-generated method stub
        super.draw(canvas);
        if (touchFlag == 0) {
            for (int i = 0; i < pointerCount; i++) {
//画十字
                canvas.drawLine(0, cy.get(i), mWidth, cy.get(i), mPaint);
                canvas.drawLine(cx.get(i), 0, cx.get(i), mHeight, mPaint);
            }
//canvas画出来的会消失
//bitmap上面的会保存下来
//所以canvas mCanvas分开画
            canvas.drawBitmap(bitmap, 0, 0, null);
        } else if (touchFlag == 1) {
//取消十字
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
    }

    private void drawLine(float cx, float cy, float x, float y) {
        mCanvas.drawLine(cx, cy, x, y, mPaint);
        invalidate();
    }

    private void drawPoint(float x, float y) {
        mCanvas.drawPoint(x, y, mPointPaint);
        invalidate();
    }

    /**
     * 在 draw()之前调用
     * 用来初始化得到屏幕尺寸
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h + 100;
        initialize();
    }

    public void initialize() {
        setFocusable(true); // 确保我们的View能获得输入焦点
        setFocusableInTouchMode(true); // 确保能接收到触屏事件
        mPaint = new Paint(Paint.DITHER_FLAG);//创建一个画笔
        mCanvas = new Canvas();
        bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888); //设置位图的宽高
        mCanvas.setBitmap(bitmap);
        mPaint.setStrokeWidth(1);//笔宽1像素
        mPaint.setColor(Color.BLUE);//设置为红笔
        // mPaint.setAntiAlias(true);//锯齿不显示
        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);//锯齿不显示
        textPaint.setTextSize(26);
        mPointPaint.setStrokeWidth(4);//笔宽1像素
        mPointPaint.setColor(Color.RED);//设置为红笔
        if (mCanvas != null) {
//请随便触摸屏幕进行测试
            mCanvas.drawColor(Color.WHITE);
            float tWidth = textPaint.measureText(START_TEXT);
            mCanvas.drawText(START_TEXT, mWidth / 2 - tWidth / 2, mHeight / 2, textPaint);
        }
        for (int j = 0; j < 5; j++) {
            cx.add(j, (float) 0);
            cy.add(j, (float) 0);
        }
    }
}