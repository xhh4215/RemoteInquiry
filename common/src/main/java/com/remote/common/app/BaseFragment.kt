package com.remote.common.app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

 abstract class BaseFragment :Fragment(){
     /***
      * 当一个fragment添加到activity的时候回调的方法
      * @param context ：fragment 添加的activity
      */
     override fun onAttach(context: Context?) {
         super.onAttach(context)

     }
     /***
      * fragment初次创建的时候调用的方法
      * @param savedInstanceState
      */
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
     }
     /***
      * 初始化fragment的布局的时候回调的方法
      * @param inflater   加载布局的布局加载器
      * @param container  初始化的布局的父布局
      * @param savedInstanceState
      * @return 初始化完成的布局
      */
     override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         return super.onCreateView(inflater, container, savedInstanceState)
     }
     /***
      * 当View视图创建的时候调用的方法
      * @param view  创建的视图
      * @param savedInstanceState
      */
     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
     }
     /**
      * 初始化相关参数
      */
     protected fun initArgs(bundle: Bundle){

     }

     /**
      * 得到当前界面的资源文件Id
      *
      * @return 资源文件Id
      */
     @LayoutRes
     protected abstract fun getContentLayoutId(): Int

     /**
      * 初始化控件
      */
     protected fun initWidget(root: View) {

     }

     /**
      * 初始化数据
      */
     protected fun initData() {

     }

     /**
      * 首次初始化数据
      */
     protected fun onFristInit() {

     }

     /**
      * 返回按键触发时调用
      *
      * @return 返回True代表我已处理返回逻辑，Activity不用自己finish。
      * 返回False代表我没有处理逻辑，Activity自己走自己的逻辑
      */
     fun onBackPressed(): Boolean {
         return false
     }

 }
