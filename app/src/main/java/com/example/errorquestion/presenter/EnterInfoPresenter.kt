package com.example.errorquestion.presenter

import android.content.Context
import com.example.errorquestion.R
import com.example.errorquestion.entity.EnterBean
import com.example.errorquestion.module.EnterInfoContract
import com.example.errorquestion.util.net.BaseObserverNoEntry
import com.example.errorquestion.util.net.RetrofitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody


class EnterInfoPresenter constructor(context: Context, view: EnterInfoContract.View) :
    EnterInfoContract.presenter {

    var context: Context = context
    var view: EnterInfoContract.View = view


    /**
     * 扫描信息
     */

    override fun getEnterInfo(access_token: String, image: String) {
        RetrofitUtil().getInstanceRetrofit()?.initRetrofitMain()?.getEnterInfo(access_token, image)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : BaseObserverNoEntry<EnterBean?>(
                context!!,
                context!!.resources.getString(R.string.handler_data)
            ) {

                override fun onSuccees(t: EnterBean?) {
                    view.setEnterInfo(t)
                }

                override fun onFailure(e: Throwable?, isNetWorkError: Boolean) {
                    view.setEnterInfoMessage(context.resources.getString(R.string.net_error))
                }
            })
    }
}
