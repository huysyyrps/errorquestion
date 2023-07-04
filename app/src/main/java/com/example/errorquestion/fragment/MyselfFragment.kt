package com.example.errorquestion.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.projection.MediaProjectionManager
import android.view.LayoutInflater
import android.view.View
import com.example.errorquestion.R
import com.example.errorquestion.util.BaseFragment
import com.example.lk_epk.util.*
import kotlinx.android.synthetic.main.fragment_myself.*
import java.util.*

class MyselfFragment : BaseFragment(), View.OnClickListener  {
    override fun getLayout(): Int {
        return R.layout.fragment_myself
    }

    override fun initView(view: View) {
        btnFinish.setOnClickListener(this)
    }

    //初始化数据
    override fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnFinish -> {
                activity?.finish()
            }
        }
    }
}