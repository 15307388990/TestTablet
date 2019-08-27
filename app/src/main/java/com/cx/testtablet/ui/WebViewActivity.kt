package com.cx.testtablet.ui

import android.app.Activity
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.cx.testtablet.R
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.base_title_layout.*



class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        iv_back.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
        tv_base_left_text.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }

        tv_base_title.text = if (intent.hasExtra("fourth_network")) getString(R.string.fourth_network)
                    else getString(R.string.wifi)

        web_view.loadUrl("https://www.hao123.com/")

        web_view.webViewClient = object : WebViewClient(){
            override  fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }
        }

        web_view.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Toast.makeText(this@WebViewActivity,"加载完成",Toast.LENGTH_SHORT).show()
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                Toast.makeText(this@WebViewActivity,"加载页面失败",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        if (web_view != null) {
            web_view.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            web_view.clearHistory();
            val viewGroup = web_view.parent as ViewGroup
            viewGroup.removeView(web_view)
            web_view.destroy()
        }
        super.onDestroy()
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        super.onBackPressed()
    }
}
