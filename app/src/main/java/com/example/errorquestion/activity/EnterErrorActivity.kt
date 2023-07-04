package com.example.errorquestion.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.annotation.RequiresApi
import com.example.errorquestion.R
import com.example.errorquestion.dialog.MainDialog
import com.example.errorquestion.entity.EnterBean
import com.example.errorquestion.entity.RemoveWrite
import com.example.errorquestion.entity.TokenBean
import com.example.errorquestion.module.EnterInfoContract
import com.example.errorquestion.module.RemoveWriteContract
import com.example.errorquestion.module.TokenInfoContract
import com.example.errorquestion.presenter.EnterInfoPresenter
import com.example.errorquestion.presenter.RemoveWritePresenter
import com.example.errorquestion.presenter.TokenInfoPresenter
import com.example.errorquestion.util.*
import com.example.errorquestion.util.camer.BaseCamer2
import com.example.errorquestion.util.camer.BitmapBack
import com.example.lk_epk.util.LogUtil
import kotlinx.android.synthetic.main.activity_enter_error.*
import java.util.regex.Pattern


class EnterErrorActivity : BaseActivity(), View.OnClickListener, TokenInfoContract.View, EnterInfoContract.View, RemoveWriteContract.View {
    lateinit var tokenInfoPresenter:TokenInfoPresenter
    lateinit var enterInfoPresenter:EnterInfoPresenter
    lateinit var removeWritePresenter:RemoveWritePresenter

    companion object{
        fun actionStart(context: Context) {
            val intent = Intent(context, EnterErrorActivity::class.java)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_error)
        tvEnterSelect.setOnClickListener(this)
        ivTakePhoto.setOnClickListener(this)
        tokenInfoPresenter = TokenInfoPresenter(this,view = this)
        enterInfoPresenter = EnterInfoPresenter(this,view = this)
        removeWritePresenter = RemoveWritePresenter(this, view = this)
//        BaseCamer2.initSurface(this,surfaceView)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, Constant.TAG_ONE)


    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvEnterSelect-> MainDialog().exitDialog(this)
            R.id.ivTakePhoto-> {
                BaseCamer2.takePicture(object :BitmapBack{
                    override fun bitmapBack(bitmap: Bitmap) {
                        if(BaseSharedPreferences.get("access_token","").isEmpty()){
                            tokenInfoPresenter.getTokenInfo(Constant.GRANTTYPE,Constant.APPKEY,Constant.SECRETKEY)
                        }else{
                            BitmapToBase64.bitmapToBase64(bitmap)?.let {
                                removeWritePresenter.getRemoveWrite(BaseSharedPreferences.get("access_token",""),
                                    it
                                )
                            }
                        }
                    }
                })
            }
        }
    }

    /**
     * token回调
     */
    override fun setTokenInfo(tokenBean: TokenBean?) {
        tokenBean?.access_token?.let {
            LogUtil.e("TAG", it)
            BaseSharedPreferences.put("access_token",it)
            tokenInfoPresenter.getTokenInfo(Constant.GRANTTYPE,Constant.APPKEY,Constant.SECRETKEY)
        }
    }
    override fun setTokenInfoMessage(message: String?) {
        message?.let { LogUtil.e("TAG", it) }
    }
    /**
     * 去手写回掉
     */
    override fun setRemoveWrite(removeWrite: RemoveWrite?) {
        var base64 = removeWrite?.image_processed
        base64?.let {
            enterInfoPresenter.getEnterInfo(BaseSharedPreferences.get("access_token",""),
                it
            )
            surfaceView.visibility = View.GONE
            imageView.visibility = View.VISIBLE
            imageView.setImageBitmap(BitmapToBase64.String2Bitmap(base64))
        }
    }

    override fun setRemoveWriteMessage(message: String?) {
        message?.let { LogUtil.e("TAG", it) }
        surfaceView.visibility = View.VISIBLE
        imageView.visibility = View.GONE
    }
    /**
     * 录入回调
     */
    override fun setEnterInfo(enterBean: EnterBean?) {
        enterBean?.let {
            var resultList = enterBean.words_result
            resultList.forEach {
                if (Pattern.matches("[0-50].*", it.words)){
                    LogUtil.e("TAG",it.words)
                }
            }
        }
    }
    override fun setEnterInfoMessage(message: String?) {
        message?.let { LogUtil.e("TAG", it) }
    }
}