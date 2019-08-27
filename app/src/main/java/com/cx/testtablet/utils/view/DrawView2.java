package com.cx.testtablet.utils.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.cx.testtablet.utils.DensityUtils;

public class DrawView2 extends View {
    float preX;
    float preY;
    //路径
    private Path path;
    //画笔
    public Paint paint = null;
    //想要保存成的图片的背景大小
    private int VIEW_WIDTH = 1000;
    private int VIEW_HEIGHT = 1000;
    //笔迹图像
    Bitmap cacheBitmap = null;
    //画布
    Canvas cacheCanvas = null;


    public DrawView2(Context context) {
        super(context);
        //this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));
//		invalidate();
        cacheBitmap  = Bitmap.createBitmap(DensityUtils.getScreenWidth(context), DensityUtils.getScreenHeight(context), Bitmap.Config.ARGB_8888);
        cacheCanvas  = new Canvas();
        path = new Path();
        cacheCanvas.setBitmap(cacheBitmap);
        paint = new Paint(Paint.DITHER_FLAG);
        //设置笔触颜色
        paint.setColor(Color.BLACK);
        //设置笔触粗细
        paint.setStrokeWidth(3);
        //设置画笔类型
        paint.setStyle(Paint.Style.STROKE);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //抗抖动
        paint.setDither(true);
    }

    public DrawView2(Context context, AttributeSet attribute) {
        super(context, attribute);
        VIEW_WIDTH = DensityUtils.getScreenWidth(context);
        VIEW_HEIGHT = DensityUtils.getScreenHeight(context);
        cacheBitmap  = Bitmap.createBitmap(DensityUtils.getScreenWidth(context), DensityUtils.getScreenHeight(context), Bitmap.Config.ARGB_8888);
        cacheCanvas  = new Canvas();
        path = new Path();
        cacheCanvas.setBitmap(cacheBitmap);
        paint = new Paint(Paint.DITHER_FLAG);
        //设置笔触颜色
        paint.setColor(Color.parseColor("#6F8AA8"));
        //设置笔触粗细
        paint.setStrokeWidth(8);
        //设置画笔类型
        paint.setStyle(Paint.Style.STROKE);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //抗抖动
        paint.setDither(true);
    }


    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawBitmap(bit, 0, 0, null);
//		canvas.drawCircle(x, y, 15, p);
        //   canvas.drawLine(x_begin, y_begin, x_end, y_end, p);
        canvas.drawBitmap(cacheBitmap, 0, 0, null);
        canvas.drawPath(path, paint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            //当按下时
            case MotionEvent.ACTION_DOWN:
                //路径起始点移动到此处，并记录
                path.moveTo(x, y);
                preX = x;
                preY = y;
                break;
            //当移动时
            case MotionEvent.ACTION_MOVE:
                //平滑曲线连接
                path.quadTo(preX, preY, x, y);
                preX = x;
                preY = y;
                path.moveTo(x, y);
                break;
            //当抬起时
            case MotionEvent.ACTION_UP:
                //在画布上画出路径
                cacheCanvas.drawPath(path, paint);
                //重置路径
                path.reset();
                break;
        }
        //更新View
        invalidate();
        return true;
    }

    public void clear(){
        //清除画布
        cacheBitmap = Bitmap.createBitmap(VIEW_WIDTH, VIEW_HEIGHT,
                Bitmap.Config.ARGB_8888);
        cacheCanvas.setBitmap(cacheBitmap);
        //更新View  invalidate vt. 使无效；使无价值
        invalidate();
    }
}
