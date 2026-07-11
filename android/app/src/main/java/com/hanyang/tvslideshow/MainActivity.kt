package com.hanyang.tvslideshow

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private val handler = Handler(Looper.getMainLooper())

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 화면 항상 켜짐 (스크린세이버/절전 진입 방지)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        webView = WebView(this).apply {
            setBackgroundColor(Color.BLACK)
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                cacheMode = WebSettings.LOAD_DEFAULT
                mediaPlaybackRequiresUserGesture = false
                loadWithOverviewMode = true
                useWideViewPort = true
                mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
            }
            webViewClient = object : WebViewClient() {
                override fun onReceivedError(
                    view: WebView?, request: WebResourceRequest?, error: WebResourceError?
                ) {
                    // 메인 페이지 로드 실패 시 30초 후 재시도
                    if (request?.isForMainFrame == true) {
                        handler.postDelayed({ loadTarget() }, 30_000)
                    }
                }
            }
        }
        setContentView(webView)
        hideSystemUi()
        loadTarget()
    }

    private fun loadTarget() {
        webView.loadUrl(getString(R.string.slideshow_url))
    }

    private fun hideSystemUi() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            )
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        webView.onResume()
    }

    override fun onPause() {
        webView.onPause()
        super.onPause()
    }
}
