package com.cx.testtablet.ui

import com.cx.testtablet.R
import com.cx.testtablet.base.BaseActivity
import kotlinx.android.synthetic.main.activity_open_thried.*
import kotlinx.android.synthetic.main.base_title_layout.*
import android.content.pm.PackageManager
import android.content.pm.PackageInfo
import com.cx.testtablet.utils.OpenApkUtils.getAppOpenIntentByPackageName
import com.cx.testtablet.utils.OpenApkUtils.getPackageContext
import android.widget.Toast


class OpenThirdActivity : BaseActivity() {
    override fun layoutId(): Int {
        return R.layout.activity_open_thried
    }

    override fun findView() {
        tv_base_title.text = getString(R.string.ttl)
        iv_back.setOnClickListener {
            showDialog()
        }

        tv_ok.setOnClickListener {
            showDialog()
        }

        tv_open_app.setOnClickListener {
            if (checkPackInfo("com.bjw.ComAssistant")) {
                openPackage()
            } else {
                Toast.makeText(this, "没有安装APP" + "",Toast.LENGTH_LONG).show()
            }
        }
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
        val pkgContext = getPackageContext(this, "com.bjw.ComAssistant")
        val intent = getAppOpenIntentByPackageName(this, "com.bjw.ComAssistant")
        if (pkgContext != null && intent != null) {
            pkgContext.startActivity(intent)
            return true
        }
        return false
    }
}
