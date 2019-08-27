package com.cx.testtablet.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.cx.testtablet.base.BaseEvent;

import org.greenrobot.eventbus.EventBus;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

       if (intent.getAction().equals("intent.action.comming.signal"))
           EventBus.getDefault().post(new BaseEvent(0,0xff000));
       else if (intent.getAction().equals("intent.action.leaveing.signal"))
           EventBus.getDefault().post(new BaseEvent(1,0xff001));

//        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction()))
//            Log.e("-----广播2：",getResultData()+"_"+intent.getAction()+"_"+intent.getDataString());
    }
}
