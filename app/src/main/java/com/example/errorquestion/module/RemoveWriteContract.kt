package com.example.errorquestion.module
import com.example.errorquestion.entity.EnterBean
import com.example.errorquestion.entity.RemoveWrite
import com.example.errorquestion.util.net.BaseEView
import com.example.errorquestion.util.net.BasePresenter
import com.example.errorquestion.util.net.BaseSView
import com.example.errorquestion.util.net.BaseView
import okhttp3.RequestBody

interface RemoveWriteContract {
    interface View : BaseSView<presenter?> {
        //获取版本信息
        @Throws(Exception::class)
        fun setRemoveWrite(removeWrite: RemoveWrite?)
        fun setRemoveWriteMessage(message: String?)
    }

    interface presenter : BasePresenter {
        //版本信息回调
        fun getRemoveWrite(access_token: String, image: String)
    }
}