package com.cx.testtablet.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.widget.Toast
import com.cx.testtablet.R

import com.cx.testtablet.utils.ActivityManager
import com.cx.testtablet.utils.dialog.Dialog
import kotlinx.android.synthetic.main.dialog_start_test_layout.view.*
import kotlinx.android.synthetic.main.layout_dialog_new.view.*

abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun layoutId(): Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        ActivityManager.getInstance().addActivity(this)
        findView()
        init()
    }

    protected abstract fun findView()
    protected abstract fun init()

    override fun onDestroy() {
        ActivityManager.getInstance().removeActivity(this)
        super.onDestroy()
    }

    protected fun showDialog(){
        Dialog.Builder(this)
                .setDialogView(R.layout.layout_dialog_new)
                .setScreenWidthP(0.5f) //设置屏幕宽度比例 0.0f-1.0f
                .setTitle("请选择检测结果")
                .setGravity(Gravity.BOTTOM)//设置Gravity
                .setWindowBackgroundP(0.5f)//设置背景透明度 0.0f-1.0f 1.0f完全不透明
                .setCancelable(false)//设置是否屏蔽物理返回键 true不屏蔽  false屏蔽
                .setCancelableOutSide(false)//设置dialog外点击是否可以让dialog消失！
                .setBuildChildListener { dialog, view, _ ->
                    val intents = Intent()
                    view!!.tv_ok.setOnClickListener {
                        dialog!!.dismiss()
                        intents.putExtra("status",2)
                        setResult(Activity.RESULT_OK,intents)
                        finish()
                    }

                    view.tv_ng.setOnClickListener {
                        dialog!!.dismiss()
                        intents.putExtra("status",1)
                        setResult(Activity.RESULT_OK,intents)
                        finish()
                    }

                    view.tv_cancel.setOnClickListener {
                        dialog!!.dismiss()
                    }
                }.show()
    }

    @SuppressLint("SetTextI18n")
    protected fun showStartDialog(title: String){
       /* Dialog.Builder(this)
                .setDialogView(R.layout.dialog_start_test_layout)
                .setScreenWidthP(0.5f) //设置屏幕宽度比例 0.0f-1.0f
                .setGravity(Gravity.CENTER)//设置Gravity
                .setWindowBackgroundP(0.3f)//设置背景透明度 0.0f-1.0f 1.0f完全不透明
                .setCancelable(true)//设置是否屏蔽物理返回键 true不屏蔽  false屏蔽
                .setCancelableOutSide(true)//设置dialog外点击是否可以让dialog消失！
                .setBuildChildListener { dialog, view, _ ->
                    view!!.tv_test_title.text = "点击确认按钮开始${title}测试"
                    view.tv_test_ok.setOnClickListener {
                        dialog!!.dismiss()
                    }

                    view.tv_test_cancel.setOnClickListener {
                        dialog!!.dismiss()
                        finish()
                    }
                }.show()*/
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        showDialog()
    }
}
