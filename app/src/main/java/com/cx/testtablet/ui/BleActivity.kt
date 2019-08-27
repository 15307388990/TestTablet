package com.cx.testtablet.ui

import com.cx.testtablet.R
import kotlinx.android.synthetic.main.activity_ble.*
import android.content.Intent
import android.provider.Settings
import com.cx.testtablet.base.BaseActivity
import kotlinx.android.synthetic.main.base_title_layout.*
import android.text.InputFilter
import android.util.Log
import com.cx.testtablet.utils.EditTextInputFilter


class BleActivity : BaseActivity() {
    companion object {
        const val OPEN_BLE = 0x004
    }
    override fun layoutId(): Int {
        return R.layout.activity_ble
    }

    override fun findView() {
        tv_open_ble.setOnClickListener {
            val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
            startActivityForResult(intent,OPEN_BLE)
        }

        iv_back.setOnClickListener {
            showDialog()
        }

        tv_base_left_text.setOnClickListener {
            showDialog()
        }

        tv_base_title.text = getString(R.string.bt)


        tv_ok.setOnClickListener {
            showDialog()
        }
        val filters = arrayOf<InputFilter>(EditTextInputFilter())
        et_cmd.filters = filters
    }

    override fun init() {
        showStartDialog(getString(R.string.bt))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == OPEN_BLE)
//            showDialog()
    }
}
