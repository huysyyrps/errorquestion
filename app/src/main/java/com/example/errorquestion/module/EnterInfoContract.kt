package com.example.errorquestion.module
import com.example.errorquestion.entity.EnterBean
import com.example.errorquestion.util.net.BaseEView
import com.example.errorquestion.util.net.BasePresenter
import com.example.errorquestion.util.net.BaseView
import okhttp3.RequestBody

interface EnterInfoContract {
    interface View : BaseView<presenter?> {
        //获取版本信息
        @Throws(Exception::class)
        fun setEnterInfo(enterBean: EnterBean?)
        fun setEnterInfoMessage(message: String?)
    }

    interface presenter : BasePresenter {
        //版本信息回调
        fun getEnterInfo(access_token: String, image: String)
    }
}