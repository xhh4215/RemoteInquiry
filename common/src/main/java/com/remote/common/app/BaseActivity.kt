package com.remote.common.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    /***
     * activity初次创建调用的方法
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindow()
       if( initArgs(intent.extras)){
           setContentView(getLayoutId())
           initWidget()
           initData()
       }else{
           finish()
       }
    }
    /***
     * 初始化窗口的方法
     */
    open  fun initWindow() {

    }

    /***
     * 初始化别的activity传递的信息的方法
     */
    open fun initArgs(bundle: Bundle?) = true

    /***
     * 获取布局id的方法
     */
    open abstract fun getLayoutId():Int

    /***
     * 初始化控件的方法
     */
    open  fun initWidget() {

    }

    /***
     * 初始化数据的方法
     */
    open  fun initData() {

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    /***
     * backpress按键点击执行的方法
     */
    override fun onBackPressed() {
        // 得到当前Activity下的所有Fragment
        @SuppressLint("RestrictedApi")
        val fragments = supportFragmentManager.fragments
        // 判断fragments是不是空的 并且fragments个数大于0
        if (fragments != null && fragments.size > 0) {
            //遍历fragments
            for (fragment in fragments) {
                // 判断是否为我们能够处理的Fragment类型
                if (fragment is BaseFragment) {
                    // 判断是否拦截了返回按钮
                    if ((fragment).onBackPressed()) {
                        // 如果有直接Return
                        return
                    }
                }
            }
        }

        super.onBackPressed()
        finish()
    }

}