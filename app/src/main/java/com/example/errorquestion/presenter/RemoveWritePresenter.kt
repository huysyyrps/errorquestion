package com.example.errorquestion.presenter

import android.content.Context
import com.example.errorquestion.R
import com.example.errorquestion.entity.EnterBean
import com.example.errorquestion.entity.RemoveWrite
import com.example.errorquestion.module.EnterInfoContract
import com.example.errorquestion.module.RemoveWriteContract
import com.example.errorquestion.util.net.BaseObserverNoEntry
import com.example.errorquestion.util.net.RetrofitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody


class RemoveWritePresenter constructor(context: Context, view: RemoveWriteContract.View) :
    RemoveWriteContract.presenter {

    var context: Context = context
    var view: RemoveWriteContract.View = view


    /**
     * 去手写
     */
    override fun getRemoveWrite(access_token: String, image: String) {
        RetrofitUtil().getInstanceRetrofit()?.initRetrofitMain()?.removeWrite(access_token, image)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : BaseObserverNoEntry<RemoveWrite?>(
                context!!,
                context!!.resources.getString(R.string.handler_data)
            ) {

                override fun onSuccees(t: RemoveWrite?) {
                    view.setRemoveWrite(t)
                }

                override fun onFailure(e: Throwable?, isNetWorkError: Boolean) {
                    view.setRemoveWriteMessage(context.resources.getString(R.string.net_error))
                }
            })
    }
}
