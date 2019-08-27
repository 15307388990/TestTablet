package com.cx.testtablet.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class DensityUtils {
    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**获取屏幕的宽度（像素）*/
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;//1080
    }

    /**获取屏幕的宽度（dp）*/
    public static int getScreenWidthDp(Context context) {
        float scale = getScreenDendity(context);
        return (int)(context.getResources().getDisplayMetrics().widthPixels / scale);//360
    }

    /**获取屏幕的高度（像素）*/
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;//1776
    }

    /**获取屏幕的高度（像素）*/
    public static int getScreenHeightDp(Context context) {
        float scale = getScreenDendity(context);
        return (int)(context.getResources().getDisplayMetrics().heightPixels / scale);//592
    }
    /**屏幕密度比例*/
    public static float getScreenDendity(Context context){
        return context.getResources().getDisplayMetrics().density;//3
    }

    /**获取状态栏的高度 72px
     * http://www.2cto.com/kf/201501/374049.html*/
    public static int getStatusBarHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> aClass = Class.forName("com.android.internal.R$dimen");
            Object object = aClass.newInstance();
            int height = Integer.parseInt(aClass.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;

        //依赖于WMS(窗口管理服务的回调)【不建议使用】
        /*Rect outRect = new Rect();
        ((Activity)context).getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        return outRect.top;*/
    }

    /**
     * 指定机型（displayMetrics.xdpi）下dp转px
     * 18dp - 50px*/
    public static int dpToPx(Context context, int dp) {
        return Math.round(((float)dp * getPixelScaleFactor(context)));
    }

    /**
     * 指定机型（displayMetrics.xdpi）下px 转 dp
     * 50px - 18dp*/
    public static int pxToDp(Context context, int px) {
        return Math.round(((float)px / getPixelScaleFactor(context)));
    }

    /**获取水平方向的dpi的密度比例值
     * 2.7653186*/
    public static float getPixelScaleFactor(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (displayMetrics.xdpi / 160.0f);
    }
}
