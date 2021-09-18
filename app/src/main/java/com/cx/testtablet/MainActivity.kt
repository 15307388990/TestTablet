package com.cx.testtablet

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import android.widget.Toast
import com.cx.testtablet.adapter.HomeAdapter
import com.cx.testtablet.ui.*
import com.cx.testtablet.utils.DensityUtils
import com.cx.testtablet.utils.GridSpaceDecoration
import com.cx.testtablet.utils.dialog.Dialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_start_test_layout.view.*
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.FileWriter
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private var list = arrayListOf<Int>()
    private lateinit var adapter: HomeAdapter

    companion object {
        const val GO_TEST = 0x001
        private var current = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        list.clear()
        for (i in 0..10) {
            list.add(0)
        }
        rc_home.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        adapter = HomeAdapter(this, list)
        rc_home.addItemDecoration(GridSpaceDecoration(DensityUtils.dip2px(this, 4f)))
        rc_home.adapter = adapter

        adapter.setOnItemClick {
            current = it
            when (it) {
                0 -> startActivityForResult(Intent(this, LcdActivity::class.java), GO_TEST)
                1 -> startActivityForResult(Intent(this, TpActivity::class.java), GO_TEST)
                2 -> startActivityForResult(Intent(this, CamActivity::class.java), GO_TEST)
                3 -> startActivityForResult(Intent(this, IRActivity::class.java), GO_TEST)
                4 -> if (check()) {
                    list[current] = 2
                    adapter.notifyItemChanged(current)
                } else {
                    list[current] = 1
                    adapter.notifyItemChanged(current)
                }
                5 -> startActivityForResult(Intent(this, WifiActivity::class.java), GO_TEST)
                /*6 -> startActivityForResult(Intent(this, TTLActivity::class.java)
                        .putExtra("type",1),GO_TEST)*/
                6 -> startActivityForResult(Intent(this, BleTestActivity::class.java), GO_TEST)
                7 -> startActivityForResult(Intent(this, SPActivity::class.java), GO_TEST)
                8 -> startActivityForResult(Intent(this, MicActivity::class.java), GO_TEST)
                9 -> startActivityForResult(Intent(this, OpenThirdActivity::class.java), GO_TEST)
                10 -> startActivityForResult(Intent(this, FourthNetworkActivity::class.java), GO_TEST)
            }
        }
        tv_title.setText("TEXT" + getVersionName());
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GO_TEST && resultCode == Activity.RESULT_OK && data != null) {
            list[current] = data.getIntExtra("status", 0)
            adapter.notifyItemChanged(current)
        }
    }

    /* private var mExitTime = 0L
     override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
         if (keyCode == KeyEvent.KEYCODE_BREAK){
             *//*if ((System.currentTimeMillis() - mExitTime) > 2000){
                Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT).show()
                mExitTime = System.currentTimeMillis()
            }*//*
            showExitDialog()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }*/

    override fun onBackPressed() {
        showExitDialog()
    }

    @SuppressLint("SetTextI18n")
    private fun showExitDialog() {
        Dialog.Builder(this)
                .setDialogView(R.layout.dialog_start_test_layout)
                .setScreenWidthP(0.5f) //设置屏幕宽度比例 0.0f-1.0f
                .setGravity(Gravity.CENTER)//设置Gravity
                .setWindowBackgroundP(0.3f)//设置背景透明度 0.0f-1.0f 1.0f完全不透明
                .setCancelable(true)//设置是否屏蔽物理返回键 true不屏蔽  false屏蔽
                .setCancelableOutSide(true)//设置dialog外点击是否可以让dialog消失！
                .setBuildChildListener { dialog, view, _ ->
                    view!!.tv_test_title.text = "确定要退出吗？退出后当前测试的记录会被清除。"
                    view.tv_test_ok.text = "确定"
                    view.tv_test_cancel.text = "取消"
                    view.tv_test_ok.setOnClickListener {
                        if (checkPermission()) {
                            removeFile(Environment.getExternalStorageDirectory().absolutePath + "/TestTablet/")
                        }
                        dialog!!.dismiss()
                        finish()
                    }

                    view.tv_test_cancel.setOnClickListener {
                        dialog!!.dismiss()
                    }
                }.show()
    }

    private fun checkPermission(): Boolean {
        return EasyPermissions.hasPermissions(this@MainActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun removeFile(filePath: String?) {
        if (filePath == null || filePath.isEmpty()) {
            return
        }
        try {
            val file = File(filePath)
            if (file.exists()) {
                removeFile(file)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    private fun removeFile(file: File) {
        //如果是文件直接删除
        if (file.isFile) {
            file.delete()
            return
        }
        //如果是目录，递归判断，如果是空目录，直接删除，如果是文件，遍历删除
        if (file.isDirectory) {
            val childFile = file.listFiles()
            if (childFile == null || childFile!!.isEmpty()) {
                file.delete()
                return
            }
            for (f in childFile!!) {
                removeFile(f)
            }
            file.delete()
        }
    }

    fun getVersionName(): String? {
        val manager = this.packageManager
        var versionNam: String? = null
        try {
            val info = manager.getPackageInfo(this.packageName, 0)
            versionNam = info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return versionNam
    }

    /**
     *
     */
    fun check(): Boolean {
        var res = true
        val file = File("/dev/ttyUSB0")
        try {
            if (!file.exists()) {
                res = false
            }
        } catch (ex: IOException) {
            res = false
            ex.printStackTrace()
        }
        return res
    }

}
