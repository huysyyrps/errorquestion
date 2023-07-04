package com.example.errorquestion.fragment

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.example.errorquestion.R
import com.example.errorquestion.activity.EnterErrorActivity
import com.example.errorquestion.util.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(), View.OnClickListener {
    override fun getLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initView(view: View) {
        ivEnterError.setOnClickListener(this)
    }

    //初始化数据
    override fun initData() {
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivEnterError->{
                activity?.let { EnterErrorActivity.actionStart(it) }
            }
        }
    }
}