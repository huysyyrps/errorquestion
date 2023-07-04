package com.example.errorquestion.util

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
abstract class BaseFragment : Fragment() {
    lateinit var activityContext: Context
    var connectStatue: Boolean = false

    companion object {
        private val TAG = "BaseFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(getLayout(), container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化控件
        initView(view)
        initData()
    }

    fun getActivityContext(context: Context) {
        activityContext = context
    }


    private fun connect() {
        Log.d(TAG, "connect")
    }

    //初始化布局
    abstract fun getLayout(): Int
    abstract fun initView(view: View)
    abstract fun initData()
}