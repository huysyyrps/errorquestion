package com.example.errorquestion.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.errorquestion.R
import com.example.errorquestion.dialog.MainDialog
import com.example.errorquestion.fragment.ErrorFragment
import com.example.errorquestion.fragment.HomeFragment
import com.example.errorquestion.fragment.MyselfFragment
import com.example.errorquestion.fragment.StudentFragment
import com.example.errorquestion.util.BaseActivity
import com.example.errorquestion.util.PermissionRequest
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    private lateinit var fragmentManager: FragmentManager
    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTagList = arrayOf("HomeFragment", "ErrorFragment", "StudentFragment", "HomeFragment")
    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    //推出程序
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            MainDialog().exitDialog(this)
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var persionTag = PermissionRequest.requestPermission(this)
        mFragmentList.add(0, HomeFragment())
        mFragmentList.add(1, ErrorFragment())
        mFragmentList.add(2, StudentFragment())
        mFragmentList.add(3, MyselfFragment())
        // 初始化首次进入时的Fragment
        fragmentManager = supportFragmentManager;
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
//        transaction.add(R.id.flFragment, currentFragmen, mFragmentTagList[0])
//        transaction.commitAllowingStateLoss()
        transaction.replace(R.id.frameLayout, HomeFragment()) //默认切换为首界面
        transaction.commit()
        radioGroupClient()
    }

    private fun radioGroupClient() {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbHome -> {
                    var fragmentManager = supportFragmentManager;
                    var transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.frameLayout, HomeFragment());
                    transaction.commit();
                }
                R.id.rbError -> {
                    var fragmentManager = supportFragmentManager;
                    var transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.frameLayout, ErrorFragment());
                    transaction.commit();
                }
                R.id.rbStudent -> {
                    var fragmentManager = supportFragmentManager;
                    var transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.frameLayout, StudentFragment());
                    transaction.commit();
                }
                R.id.rbMySele -> {
                    var fragmentManager = supportFragmentManager;
                    var transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.frameLayout, MyselfFragment());
                    transaction.commit();
                }
            }
        }
    }
}