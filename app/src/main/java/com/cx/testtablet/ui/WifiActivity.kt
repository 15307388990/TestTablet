package com.cx.testtablet.ui


import android.app.Activity
import com.cx.testtablet.R
import kotlinx.android.synthetic.main.activity_wifi.*
import android.content.Intent
import android.net.ConnectivityManager
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.cx.testtablet.base.BaseActivity
import com.cx.testtablet.utils.dialog.Dialog
import kotlinx.android.synthetic.main.base_title_layout.*
import kotlinx.android.synthetic.main.dialog_test_wifi_layout.view.*


class WifiActivity : BaseActivity() {
    companion object {
        const val OPEN_WIFI = 0x002
        const val TEST_WIFI = 0x003
    }
    override fun layoutId(): Int {
        return R.layout.activity_wifi
    }

    override fun findView() {
        btn_wifi.setOnClickListener {
            val wifiSettingsIntent = Intent("android.settings.WIFI_SETTINGS")
            startActivityForResult(wifiSettingsIntent,OPEN_WIFI)
        }

        btn_web.setOnClickListener {
            if (isWifi()) startActivityForResult(Intent(this,WebViewActivity::class.java), TEST_WIFI)
            else Toast.makeText(this,"请检查WiFi是否连接",Toast.LENGTH_SHORT).show()
        }

        iv_back.setOnClickListener {
            showDialog()
        }

        tv_base_left_text.setOnClickListener {
            showDialog()
        }

        tv_base_title.text = getString(R.string.wifi)
    }

    override fun init() {
        showStartDialog(getString(R.string.wifi))
    }



    private fun isWifi(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivityManager.activeNetworkInfo
        return info != null && info.type == ConnectivityManager.TYPE_WIFI
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        Log.e("wifi: ",""+(requestCode == OPEN_WIFI) +"_"+ (requestCode == Activity.RESULT_OK)+"_"+isWifi())
        if (requestCode == OPEN_WIFI  && isWifi()){
            showWiFiDialog()
        }else if (requestCode == TEST_WIFI && resultCode == Activity.RESULT_OK){
            showDialog()
        }
    }

    private fun showWiFiDialog(){
        Dialog.Builder(this)
                .setDialogView(R.layout.dialog_test_wifi_layout)
                .setScreenWidthP(0.5f) //设置屏幕宽度比例 0.0f-1.0f
                .setGravity(Gravity.CENTER)//设置Gravity
                .setWindowBackgroundP(0.4f)//设置背景透明度 0.0f-1.0f 1.0f完全不透明
                .setCancelable(false)//设置是否屏蔽物理返回键 true不屏蔽  false屏蔽
                .setCancelableOutSide(false)//设置dialog外点击是否可以让dialog消失！
                .setBuildChildListener { dialog, view, _ ->
                    view!!.tv_wifi_ok.setOnClickListener {
                        dialog!!.dismiss()
                        startActivityForResult(Intent(this,WebViewActivity::class.java), TEST_WIFI)
                    }
                }.show()
    }
}
