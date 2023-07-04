package com.example.errorquestion.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.example.errorquestion.R
import kotlinx.android.synthetic.main.dialog_exit.*


class MainDialog {
    /**
     * 初始化重新扫描扫描dialog
     */
    private lateinit var dialog: MaterialDialog


    /**
     * 生成报告
     */
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingPermission")
    fun exitDialog(activity: Activity) {
        dialog = MaterialDialog(activity)
            .cancelable(false)
            .show {
                customView(    //自定义弹窗
                    viewRes = R.layout.dialog_exit,//自定义文件
                    dialogWrapContent = true,    //让自定义宽度生效
                    scrollable = true,            //让自定义宽高生效
                    noVerticalPadding = true    //让自定义高度生效
                )
                cornerRadius(16f)
            }
        dialog.tvContext.text = "${activity.resources.getString(R.string.exit_sure)}"

        dialog.btnExitCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.btnExitSure.setOnClickListener {
            dialog.dismiss()
            activity.finish()
        }
    }
}