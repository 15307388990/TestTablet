package com.cx.testtablet.ui

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.Toast
import com.cx.testtablet.R
import com.cx.testtablet.base.BaseActivity
import com.cx.testtablet.ui.WifiActivity.Companion.OPEN_WIFI
import com.cx.testtablet.ui.WifiActivity.Companion.TEST_WIFI
import com.cx.testtablet.utils.dialog.Dialog
import kotlinx.android.synthetic.main.activity_fourth_network.*
import kotlinx.android.synthetic.main.base_title_layout.*
import kotlinx.android.synthetic.main.dialog_test_wifi_layout.view.*


class FourthNetworkActivity : BaseActivity() {
    companion object {
        const val OPEN_4G = 0x004
        const val TEST_4G = 0x005
    }
    override fun layoutId(): Int {
        return R.layout.activity_fourth_network
    }

    override fun findView() {
        btn_4g.setOnClickListener {
            val intent = Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS)
            startActivityForResult(intent,OPEN_WIFI)
        }

        btn_web.setOnClickListener {
            if (isForthNetwork()) startActivityForResult(Intent(this,WebViewActivity::class.java)
                    .putExtra("fourth_network",true), TEST_4G)
            else Toast.makeText(this,"请检查移动数据是否连接",Toast.LENGTH_SHORT).show()
        }

        iv_back.setOnClickListener {
            showDialog()
        }

        tv_base_left_text.setOnClickListener {
            showDialog()
        }

        tv_base_title.text = getString(R.string.fourth_network)
    }

    override fun init() {
        showStartDialog(getString(R.string.fourth_network))
    }



    private fun isForthNetwork(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return networkInfo.isConnected
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        Log.e("wifi: ",""+(requestCode == OPEN_WIFI) +"_"+ (requestCode == Activity.RESULT_OK)+"_"+isWifi())
        if (requestCode == OPEN_4G  && isForthNetwork()){
            showWiFiDialog()
        }else if (requestCode == TEST_4G && resultCode == Activity.RESULT_OK){
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
                    view!!.tv_dialog_title.text = "移动网络数据链接成功，点击ok，打开网页"
                    view!!.tv_wifi_ok.setOnClickListener {
                        dialog!!.dismiss()
                        startActivityForResult(Intent(this,WebViewActivity::class.java)
                                .putExtra("fourth_network",true), TEST_4G)
                    }
                }.show()
    }

}
