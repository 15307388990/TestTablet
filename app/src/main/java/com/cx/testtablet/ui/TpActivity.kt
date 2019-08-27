package com.cx.testtablet.ui

import android.os.Build
import com.cx.testtablet.base.BaseActivity
import kotlinx.android.synthetic.main.activity_tp.*
import kotlinx.android.synthetic.main.base_title_layout.*
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
import android.view.View
import com.cx.testtablet.R


class TpActivity : BaseActivity() {
    override fun layoutId(): Int {
        return R.layout.activity_tp
    }

    override fun findView() {

        iv_back.setOnClickListener { finish() }
        tv_base_left_text.setOnClickListener { finish() }

        tv_base_title.text = getString(R.string.tp)

        tv_base_right_text.text = "Clean"
        tv_base_right_text.setOnClickListener {
            //            draw_view.clear()
        }

        iv_back.setOnClickListener {
            showDialog()
        }

        tv_base_left_text.setOnClickListener {
            showDialog()
        }
    }

    override fun init() {
        showStartDialog(getString(R.string.tp))
    }

    private fun hideNavigationBar() {
        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            val decorView = window.decorView
            decorView.requestFocus()
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }
}
