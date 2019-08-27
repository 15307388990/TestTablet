package com.cx.testtablet.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.Stack;

/**
 * Created by Administrator on 2018/3/18 0018.
 */

public class ActivityManager {

    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    private ActivityManager(){

    }

    public static synchronized ActivityManager getInstance(){
        if (instance == null){
            instance = new ActivityManager();
        }
        return instance;
    }

    public synchronized void addActivity(Activity activity){
        if (activityStack == null){
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    public Activity currentActivtiy(){ return activityStack.lastElement(); }

    public void finishActivity(){ finishActivity(activityStack.lastElement());}

    public void removeActivity(Activity activity){
        if (activity != null){
            activityStack.remove(activity);
        }
    }

    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            Log.i("TAG", "ActivityManager关闭了：" + activity.getClass().getName());
        }
    }

    public void finishActivity(Class<?> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (activityStack.get(i).getClass().equals(cls)) {
                finishActivity(activityStack.get(i));
                removeActivity(activityStack.get(i));
                return;
            }
        }
    }


    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.clear();
    }


    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            android.app.ActivityManager activityMgr = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
