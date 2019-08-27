package com.cx.testtablet.ui

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import com.cx.testtablet.R
import com.cx.testtablet.base.BaseActivity
import com.cx.testtablet.utils.OpenApkUtils
import kotlinx.android.synthetic.main.activity_ble_test.*
import kotlinx.android.synthetic.main.base_title_layout.*

class BleTestActivity : BaseActivity() {
    override fun layoutId(): Int {
        return R.layout.activity_ble_test
    }

    override fun findView() {
        tv_open_ble.setOnClickListener {
            val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
            startActivity(intent)
        }

        tv_ble.setOnClickListener {
            if (checkPackInfo("com.bjw.ComAssistant")) {
                openPackage()
            } else {
                Toast.makeText(this, "没有安装APP" + "", Toast.LENGTH_LONG).show()
            }
        }

        tv_ok.setOnClickListener {
            showDialog()
        }

        iv_back.setOnClickListener {
            showDialog()
        }

        tv_base_title.text = getString(R.string.bt)
    }

    override fun init() {

    }

    private fun checkPackInfo(packname: String): Boolean {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packname, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return packageInfo != null
    }

    private fun openPackage(): Boolean {
        val pkgContext = OpenApkUtils.getPackageContext(this, "com.bjw.ComAssistant")
        val intent = OpenApkUtils.getAppOpenIntentByPackageName(this, "com.bjw.ComAssistant")
        if (pkgContext != null && intent != null) {
            pkgContext.startActivity(intent)
            return true
        }
        return false
    }
}
