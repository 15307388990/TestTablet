package com.cx.testtablet.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cam.*
import kotlinx.android.synthetic.main.base_title_layout.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import android.support.v4.content.FileProvider
import android.os.Build
import android.os.Environment
import java.io.File
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.cx.testtablet.R
import com.cx.testtablet.base.BaseActivity




class CamActivity : BaseActivity(),EasyPermissions.PermissionCallbacks{

    companion object {
        val CAMERA = 0x002
        val VIDEO = 0x003
    }

    override fun layoutId(): Int {
        return R.layout.activity_cam
    }

    override fun findView(){
        iv_back.setOnClickListener {
            showDialog()
        }
        tv_base_left_text.setOnClickListener { showDialog() }

        tv_base_title.text = getString(R.string.cam)

        /*tv_camera.setOnClickListener {
            if (!checkPermission())
                requestPermission()
            else{
                //打开照相机
               *//* val openCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val imageUri = getOutputMediaFileUri(true)
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

                //Android7.0添加临时权限标记，此步千万别忘了
                openCameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                startActivityForResult(openCameraIntent, CAMERA)*//*
                val imageCaptureIntent = Intent("android.media.action.STILL_IMAGE_CAMERA")
                startActivityForResult(imageCaptureIntent, CAMERA)
            }
        }

        tv_video.setOnClickListener {
            if (!checkPermission())
                requestPermission()
            else{
                //打开照相机
               *//* val openCameraIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                val imageUri = getOutputMediaFileUri(false)
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                openCameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1)
                //Android7.0添加临时权限标记，此步千万别忘了
                openCameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                startActivityForResult(openCameraIntent, CAMERA)*//*
                val imageCaptureIntent = Intent("android.media.action.STILL_IMAGE_CAMERA")
                startActivityForResult(imageCaptureIntent, VIDEO)
            }
        }*/

        tv_open.setOnClickListener {
            val imageCaptureIntent = Intent("android.media.action.STILL_IMAGE_CAMERA")
            startActivityForResult(imageCaptureIntent, CAMERA)
        }
    }

    override fun init() {
        showStartDialog(getString(R.string.cam))
    }

    private fun checkPermission(): Boolean{
        return EasyPermissions.hasPermissions(this@CamActivity, Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO)
    }

    private fun requestPermission(){
        EasyPermissions.requestPermissions(this@CamActivity,"需要获得拍照、录像和存储权限",0,
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
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

    private fun getOutputMediaFileUri(boolean: Boolean): Uri? {
        var mediaFile: File? = null
        val cameraPath: String
        try {
            val mediaStorageDir = File(Environment.getExternalStorageDirectory().absolutePath+"/TestTablet/")
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null
                }
            }
            mediaFile = File(mediaStorageDir.path
                    + File.separator
                    + "${System.currentTimeMillis()}".plus(if (boolean) ".jpg" else ".mp4"))//注意这里需要和filepaths.xml中配置的一样
            cameraPath = mediaFile!!.absolutePath

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {// sdk >= 24  android7.0以上
            FileProvider.getUriForFile(this@CamActivity,
                    this.applicationContext.packageName + ".provider", //与清单文件中android:authorities的值保持一致
                    mediaFile!!)
        } else {
            Uri.fromFile(mediaFile)//或者 Uri.isPaise("file://"+file.toString()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA -> {
                    Toast.makeText(this, "拍照返回", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "录像返回", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
