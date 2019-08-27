package com.cx.testtablet.ui

import android.support.v4.content.ContextCompat
import com.cx.testtablet.R
import com.cx.testtablet.base.BaseActivity
import kotlinx.android.synthetic.main.activity_lcd.*
import kotlinx.android.synthetic.main.base_title_layout.*

class LcdActivity : BaseActivity() {
    override fun layoutId(): Int {
       return R.layout.activity_lcd
    }

    override fun findView() {
        view_lcd_white.setOnClickListener {
            view_bg.setBackgroundColor(ContextCompat.getColor(this,R.color.colorWhite))
        }

        view_lcd_black.setOnClickListener {
            view_bg.setBackgroundColor(ContextCompat.getColor(this,R.color.colorBlack))
        }

        view_lcd_red.setOnClickListener {
            view_bg.setBackgroundColor(ContextCompat.getColor(this,R.color.colorRed))
        }

        view_lcd_green.setOnClickListener {
            view_bg.setBackgroundColor(ContextCompat.getColor(this,R.color.colorGreen))
        }

        view_lcd_blue.setOnClickListener {
            view_bg.setBackgroundColor(ContextCompat.getColor(this,R.color.colorBlue))
        }

        view_lcd_yellow.setOnClickListener {
            view_bg.setBackgroundColor(ContextCompat.getColor(this,R.color.colorYellow))
        }

        tv_base_title.text = getString(R.string.lcd)
        iv_back.setOnClickListener {
            showDialog()
        }

        tv_base_left_text.setOnClickListener {
            showDialog()
        }
    }

    override fun init(){

    }
}
