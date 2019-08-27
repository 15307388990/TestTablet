package com.cx.testtablet.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.view.Gravity
import android.view.WindowManager
import com.cx.testtablet.R
import com.cx.testtablet.base.BaseActivity
import com.cx.testtablet.utils.PlayMusicUtils
import com.cx.testtablet.utils.dialog.Dialog
import kotlinx.android.synthetic.main.activity_sp.*
import kotlinx.android.synthetic.main.base_title_layout.*
import kotlinx.android.synthetic.main.dialog_start_test_layout.view.*
import kotlinx.android.synthetic.main.layout_dialog_new.view.*

class SPActivity : BaseActivity() {
    private var currentVolume = 0
    private var maxVolume = 0

    override fun layoutId(): Int{
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        return R.layout.activity_sp
    }

    override fun findView() {
        tv_base_title.text = getString(R.string.sp)

        iv_back.setOnClickListener {
            showDialogs()
        }

        tv_base_left_text.setOnClickListener {
            showDialogs()
        }
    }

    override fun init() {
        val am = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        currentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC)
        pb_volume.max = maxVolume
        pb_volume.progress = currentVolume


        iv_volume_reduction.setOnClickListener {
            if (currentVolume - 2 > 0)
                currentVolume -= 2
            else currentVolume = 0
            pb_volume.progress = currentVolume
            am.setStreamVolume(AudioManager.STREAM_MUSIC,currentVolume,AudioManager.FLAG_PLAY_SOUND)
            PlayMusicUtils.getmPlayMusicUtils().toPlay(this)
        }

        iv_volume_add.setOnClickListener {
            if (currentVolume + 2 < maxVolume)
                currentVolume += 2
            else currentVolume = maxVolume
            pb_volume.progress = currentVolume
            am.setStreamVolume(AudioManager.STREAM_MUSIC,currentVolume,AudioManager.FLAG_PLAY_SOUND)
            PlayMusicUtils.getmPlayMusicUtils().toPlay(this)
        }

//        showStartDialogs(getString(R.string.sp))
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        showDialogs()
    }

    @SuppressLint("SetTextI18n")
    private fun showStartDialogs(title: String){
        Dialog.Builder(this)
                .setDialogView(R.layout.dialog_start_test_layout)
                .setScreenWidthP(0.5f) //设置屏幕宽度比例 0.0f-1.0f
                .setGravity(Gravity.CENTER)//设置Gravity
                .setWindowBackgroundP(0.3f)//设置背景透明度 0.0f-1.0f 1.0f完全不透明
                .setCancelable(true)//设置是否屏蔽物理返回键 true不屏蔽  false屏蔽
                .setCancelableOutSide(true)//设置dialog外点击是否可以让dialog消失！
                .setBuildChildListener { dialog, view, _ ->
                    view!!.tv_test_title.text = "点击确认按钮开始${title}测试"
                    view.tv_test_ok.setOnClickListener {
                        PlayMusicUtils.getmPlayMusicUtils().toPlay(this)
                        dialog!!.dismiss()
                    }

                    view.tv_test_cancel.setOnClickListener {
                        dialog!!.dismiss()
                        finish()
                    }
                }.show()
    }

    private fun showDialogs(){
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
                        PlayMusicUtils.getmPlayMusicUtils().toDestroy()
                        intents.putExtra("status",2)
                        setResult(Activity.RESULT_OK,intents)
                        finish()
                    }

                    view.tv_ng.setOnClickListener {
                        dialog!!.dismiss()
                        PlayMusicUtils.getmPlayMusicUtils().toDestroy()
                        intents.putExtra("status",1)
                        setResult(Activity.RESULT_OK,intents)
                        finish()
                    }

                    view.tv_cancel.setOnClickListener {
                        dialog!!.dismiss()
                    }
                }.show()
    }
}
