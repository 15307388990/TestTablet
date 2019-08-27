package com.cx.testtablet.ui

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cx.testtablet.R
import com.cx.testtablet.base.BaseActivity
import com.cx.testtablet.utils.MediaRecordUtil
import com.cx.testtablet.utils.PlayMusicListener
import com.cx.testtablet.utils.PlayMusicUtils
import kotlinx.android.synthetic.main.activity_mic.*
import kotlinx.android.synthetic.main.base_title_layout.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class MicActivity : BaseActivity(),EasyPermissions.PermissionCallbacks {
    private lateinit var recordingUtil: MediaRecordUtil
    private var recordFlag = false
    private var playRecordFlag = false
    private var micPath: String? = ""
    override fun layoutId(): Int {
        return R.layout.activity_mic
    }

    override fun findView() {
        tv_recording.setOnClickListener {
            if (checkPermission()) {
                recordFlag = !recordFlag
                if (recordFlag) {
                    tv_recording.text = "结束检测"
                    recordingUtil.startRecord()
                } else {
                    tv_recording.text = "开始检测"
                    tv_recording.visibility = View.GONE
                    ll_play.visibility = View.VISIBLE
                    tv_mic_ok.visibility = View.VISIBLE
                    recordingUtil.stopRecord()
                }
            }else requestPermission()
        }

        tv_play.setOnClickListener {
            if (micPath!!.isNotEmpty() && !playRecordFlag)
                PlayMusicUtils.getmPlayMusicUtils().toPlayWithListener(micPath,object : PlayMusicListener{
                    override fun onStart() {
                        tv_play.text = "暂停播放"
                        playRecordFlag = true
                    }

                    override fun onCompletion() {
                        tv_play.text = "重新播放"
                        playRecordFlag = false
                    }

                    override fun onError() {
                        tv_play.text = "播放失败"
                        playRecordFlag = false
                    }
                })
            else {
                playRecordFlag = false
                tv_play.text = "重新播放"
                PlayMusicUtils.getmPlayMusicUtils().stopPlay()
            }
        }

        tv_retest.setOnClickListener {
            tv_recording.visibility = View.VISIBLE
            ll_play.visibility = View.GONE
            tv_mic_ok.visibility = View.GONE
            micPath = ""
        }

        iv_back.setOnClickListener {
            showDialog()
        }

        tv_base_left_text.setOnClickListener {
            showDialog()
        }

        tv_mic_ok.setOnClickListener {
            showDialog()
        }
    }

    override fun init() {
        recordingUtil = MediaRecordUtil()
        recordingUtil.setOnAudioStatusUpdateListener(object : MediaRecordUtil.OnAudioStatusUpdateListener{
            override fun onUpdate(db: Double, time: Long) {

            }

            override fun onStop(filePath: String?) {
                micPath = filePath
            }
        })

        tv_base_title.text = getText(R.string.mic)

        showStartDialog(getString(R.string.mic))
    }

    private fun checkPermission(): Boolean{
        return EasyPermissions.hasPermissions(this@MicActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO)
    }

    private fun requestPermission(){
        EasyPermissions.requestPermissions(this@MicActivity,"需要获得录音和存储权限",0,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }
}
