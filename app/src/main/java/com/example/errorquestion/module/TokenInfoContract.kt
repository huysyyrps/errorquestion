package com.example.errorquestion.module
import com.example.errorquestion.entity.EnterBean
import com.example.errorquestion.entity.TokenBean
import com.example.errorquestion.util.net.BaseEView
import com.example.errorquestion.util.net.BasePresenter
import okhttp3.RequestBody

interface TokenInfoContract {
    interface View : BaseEView<presenter?> {
        //获取版本信息
        @Throws(Exception::class)
        fun setTokenInfo(tokenBean: TokenBean?)
        fun setTokenInfoMessage(message: String?)
    }

    interface presenter : BasePresenter {
        //版本信息回调
//        fun getTokenInfo(company: RequestBody?)
        fun getTokenInfo(grant_type: String, client_id: String, client_secret: String)
    }
}