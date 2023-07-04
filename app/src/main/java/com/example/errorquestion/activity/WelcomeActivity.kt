package com.example.errorquestion.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import android.view.WindowManager
import com.example.errorquestion.MyApplication
import com.example.errorquestion.R
import com.example.errorquestion.data.BannerData
import com.example.errorquestion.util.BaseActivity
import com.example.errorquestion.adapter.ImageAdapter
import com.example.lk_epk.util.LogUtil
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class WelcomeActivity : BaseActivity() {
    var countDownTimer:CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        setContentView(R.layout.activity_welcome)
        val imgList = BannerData.setBannerData()
        banner.apply {
            setIndicator(CircleIndicator(MyApplication.context))
                .setAdapter(ImageAdapter(imgList), true)
        }
        btnIn.setOnClickListener {
            countDownTimer?.cancel()
            MainActivity.actionStart(this)
            finish()
        }
        countDownTimer = object : CountDownTimer(6000, 1000) {
            //1000ms运行一次onTick里面的方法
            override fun onFinish() {
                MainActivity.actionStart(this@WelcomeActivity)
                finish()
            }

            override fun onTick(millisUntilFinished: Long) {
                CoroutineScope(Dispatchers.Main).launch {
                    tvTime.text = "${millisUntilFinished/1000}s 跳过"
                }
            }
        }.start()
    }
    /**
     * 隐藏状态栏
     */
    private fun hideStatusBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}
