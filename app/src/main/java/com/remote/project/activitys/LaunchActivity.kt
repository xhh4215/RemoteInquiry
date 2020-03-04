package com.remote.project.activitys
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.util.Property
import android.widget.Toast
import com.remote.common.app.BaseActivity
import com.remote.hklibrary.helper.INetHelper
import com.remote.project.App
import com.remote.project.R
import kotlinx.android.synthetic.main.activity_launch.*
import net.qiujuer.genius.res.Resource
import net.qiujuer.genius.ui.compat.UiCompat

class LaunchActivity : BaseActivity() {
    private lateinit var mBgDrawable:ColorDrawable
    override fun getLayoutId()= R.layout.activity_launch
    override fun initWidget() {
        super.initWidget()
        val color = UiCompat.getColor(resources, R.color.colorAccent)
        val colorDrawable = ColorDrawable(color)
        Launch.background =colorDrawable
        mBgDrawable = colorDrawable

    }

    override fun initData() {
        super.initData()
         startAnim(0.5f, Runnable {
              if(App.helper().onInit()) {

                  Intent(LaunchActivity@this, MainActivity::class.java)
                      .apply {
                          startActivity(this)
                          finish()
                      }
              }


         })
    }

    fun startAnim( endProgress:Float, endCallback:Runnable){
        val finalColor = Resource.Color.WHITE
        val argbEvaluator = ArgbEvaluator()
        val endColor = argbEvaluator.evaluate(endProgress, mBgDrawable.color, finalColor) as Int
        val valueAnimator =
            ObjectAnimator.ofObject<LaunchActivity, Any>(this, property, argbEvaluator, endColor)
        valueAnimator.duration = 1500
        valueAnimator.setIntValues(mBgDrawable.color, endColor)
        valueAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationCancel(animation)
                endCallback.run()
            }
        })
        valueAnimator.start()
    }

    private val property = object : Property<LaunchActivity, Any>(Any::class.java, "color") {
        override fun get(`object`: LaunchActivity): Any {
            return `object`.mBgDrawable.color
        }

        override fun set(`object`: LaunchActivity, value: Any) {
            `object`.mBgDrawable.color = value as Int
        }
    }
}
