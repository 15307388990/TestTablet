package com.cx.testtablet.ui

import android.serialport.SerialPortFinder
import android.text.InputFilter
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.cx.testtablet.R
import com.cx.testtablet.base.BaseActivity
import com.cx.testtablet.utils.AllCapTransformationMethod
import kotlinx.android.synthetic.main.activity_ttl.*
import com.cx.testtablet.R.array.baudrates
import com.cx.testtablet.comn.Device
import com.cx.testtablet.comn.SerialPortManager
import com.cx.testtablet.comn.message.IMessage
import com.cx.testtablet.utils.EditTextInputFilter
import com.cx.testtablet.utils.PrefHelper
import com.cx.testtablet.utils.PreferenceKeys
import kotlinx.android.synthetic.main.base_title_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class TTLActivity : BaseActivity(), AdapterView.OnItemSelectedListener {
    private var mDevices: Array<String>? = null
    private var mBaudrates: Array<String>? = null
    private var mDevice: Device? = null

    private var mDeviceIndex: Int = 0
    private var mBaudrateIndex: Int = 0

    private var mOpened = false
    private var stringBuffer = StringBuffer()

    override fun layoutId(): Int {
        return R.layout.activity_ttl
    }

    override fun findView() {
        EventBus.getDefault().register(this)

        iv_back.setOnClickListener {
            showDialog()
        }
        tv_base_left_text.setOnClickListener {
            showDialog()
        }

        tv_base_title.text = if (intent.hasExtra("type")) getString(R.string.bt) else getString(R.string.ttl)

        et_cmd.transformationMethod = AllCapTransformationMethod(true)

        tv_open_device.setOnClickListener {
            if (mOpened) {
                SerialPortManager.instance().close()
                mOpened = false
            } else {
                // 保存配置
                PrefHelper.getDefault().saveInt(PreferenceKeys.SERIAL_PORT_DEVICES, mDeviceIndex)
                PrefHelper.getDefault().saveInt(PreferenceKeys.BAUD_RATE, mBaudrateIndex)

                mOpened = SerialPortManager.instance().open(mDevice) != null
                if (mOpened) {
                    Toast.makeText(this, "成功打开串口",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "打开串口失败",Toast.LENGTH_SHORT).show()
                }
            }
            updateViewState(mOpened)
        }

        tv_send_data.setOnClickListener {
            if (TextUtils.isEmpty(et_cmd.text.toString().trim()) || et_cmd.text.toString().trim().length % 2 != 0) {
                Toast.makeText(this, "无效数据", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            SerialPortManager.instance().sendCommand(et_cmd.text.toString().trim())
        }

        tv_ok.setOnClickListener {
            showDialog()
        }

        val filters = arrayOf<InputFilter>(EditTextInputFilter())
        et_cmd.filters = filters
    }

    override fun init() {

        showStartDialog(getString(R.string.ttl))

        val serialPortFinder = SerialPortFinder()
        mDevices = serialPortFinder.allDevicesPath
        if (mDevices!!.isEmpty()) {
            mDevices = arrayOf(getString(R.string.no_serial_device))
        }

        mBaudrates = resources.getStringArray(baudrates)

        mDeviceIndex = PrefHelper.getDefault().getInt(PreferenceKeys.SERIAL_PORT_DEVICES, 0)
        mDeviceIndex = if (mDeviceIndex >= mDevices!!.size) mDevices!!.size - 1 else mDeviceIndex
        mBaudrateIndex = PrefHelper.getDefault().getInt(PreferenceKeys.BAUD_RATE, 0)

        mDevice = Device(mDevices!![mDeviceIndex], mBaudrates!![mBaudrateIndex])


        val deviceAdapter = ArrayAdapter<String>(this, R.layout.spinner_default_item, mDevices)
        deviceAdapter.setDropDownViewResource(R.layout.spinner_item)
        spinner_devices.adapter = deviceAdapter
        spinner_devices.onItemSelectedListener = this

        val baudrateAdapter = ArrayAdapter(this, R.layout.spinner_default_item, mBaudrates)
        baudrateAdapter.setDropDownViewResource(R.layout.spinner_item)
        spinner_baudrate.setAdapter(baudrateAdapter)
        spinner_baudrate.setOnItemSelectedListener(this)

        spinner_devices.setSelection(mDeviceIndex)
        spinner_baudrate.setSelection(mBaudrateIndex)

        updateViewState(mOpened)

        stringBuffer.delete(0,stringBuffer.length)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when (p0!!.id) {
            R.id.spinner_devices -> {
                mDeviceIndex = p2
                mDevice!!.path = mDevices!![mDeviceIndex]
            }
            R.id.spinner_baudrate -> {
                mBaudrateIndex = p2
                mDevice!!.baudrate = mBaudrates!![mBaudrateIndex]
            }
        }
    }

    private fun updateViewState(isSerialPortOpened: Boolean) {

        val stringRes = if (isSerialPortOpened) R.string.close_serial_port else R.string.open_serial_port

        tv_open_device.setText(stringRes)

        spinner_devices.isEnabled = !isSerialPortOpened
        spinner_baudrate.isEnabled = !isSerialPortOpened
        tv_send_data.isEnabled = isSerialPortOpened
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(message: IMessage) {
        // 收到时间，刷新界面
        if(stringBuffer.toString().length > 300)
            stringBuffer.delete(0,stringBuffer.toString().length)
        if (stringBuffer.toString().length != 0)
            stringBuffer.append("\n")
        stringBuffer.append(message.message)

        tv_history_data.text = stringBuffer.toString()
    }
}
