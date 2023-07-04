package com.example.errorquestion.presenter

import android.content.Context
import com.example.errorquestion.R
import com.example.errorquestion.entity.EnterBean
import com.example.errorquestion.entity.TokenBean
import com.example.errorquestion.module.EnterInfoContract
import com.example.errorquestion.module.TokenInfoContract
import com.example.errorquestion.util.net.BaseObserverNoEntry
import com.example.errorquestion.util.net.RetrofitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody


class TokenInfoPresenter constructor(context: Context, view: TokenInfoContract.View) : TokenInfoContract.presenter {

    var context: Context = context
    var view: TokenInfoContract.View = view


    /**
     * token
     */
//    override fun getVersionInfo(company: RequestBody?) {
//        RetrofitUtil().getInstanceRetrofit()?.initRetrofitMain()?.getTokenInfo(company)
    override fun getTokenInfo(grant_type: String, client_id: String, client_secret: String) {
        RetrofitUtil().getInstanceRetrofit()?.initRetrofitMain()?.getTokenInfo(grant_type, client_id, client_secret)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : BaseObserverNoEntry<TokenBean?>(
                context!!,
                context!!.resources.getString(R.string.handler_data)
            ) {
                override fun onSuccees(t: TokenBean?) {
                    view.setTokenInfo(t)
                }

                override fun onFailure(e: Throwable?, isNetWorkError: Boolean) {
                    view.setTokenInfoMessage(context.resources.getString(R.string.net_error))
                }
            })
    }
}
