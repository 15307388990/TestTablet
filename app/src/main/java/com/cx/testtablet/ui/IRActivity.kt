package com.cx.testtablet.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.UserHandle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.widget.Toast
import com.cx.testtablet.R
import com.cx.testtablet.base.BaseActivity
import com.cx.testtablet.base.BaseEvent
import com.cx.testtablet.receiver.MyReceiver
import kotlinx.android.synthetic.main.activity_ir.*
import kotlinx.android.synthetic.main.base_title_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import android.net.ConnectivityManager
import android.net.wifi.WifiManager



class IRActivity : BaseActivity() {
    private lateinit var myReceiver: MyReceiver
    private var IRIN = false
    private var IRLIVE = false
    override fun layoutId(): Int {
        return R.layout.activity_ir
    }

    override fun findView() {
        EventBus.getDefault().register(this)

        iv_back.setOnClickListener {
            showDialog()
        }

        tv_base_left_text.setOnClickListener {
            showDialog()
        }

        tv_base_title.text = getString(R.string.ir)
    }

    override fun init() {
        myReceiver = MyReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction("intent.action.comming.signal")
        intentFilter.addAction("intent.action.leaveing.signal")
        registerReceiver(myReceiver,intentFilter)
//        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun TestIR(event: BaseEvent){
        when (event.tag){
            0xff000 -> {
                IRIN =  true
                Toast.makeText(this,"接收到进入广播",Toast.LENGTH_SHORT).show()
            }
            0xff001 ->{
                IRLIVE = true
                Toast.makeText(this,"接收到离开广播",Toast.LENGTH_SHORT).show()
            }
        }

        if (IRIN && IRLIVE)
            tv_ir_ok.visibility = View.VISIBLE
        else
            tv_ir_ok.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        unregisterReceiver(myReceiver)
    }
}
