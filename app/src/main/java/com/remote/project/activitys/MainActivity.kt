package com.remote.project.activitys


import android.os.Bundle
import android.text.Layout
import android.view.Gravity
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.remote.common.app.BaseActivity
import com.remote.project.App
import com.remote.project.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity() {
    private var logIdOne = -1
    private var logIdTwo = -1
    private lateinit var timer: Timer
    override fun getLayoutId() = R.layout.activity_main
    override fun initArgs(bundle: Bundle?): Boolean {
        return super.initArgs(bundle)
        if (bundle!=null){
            logIdOne = bundle.getInt("id1")
            logIdTwo = bundle.getInt("id2")
        }

    }
    override fun initWidget() {
        super.initWidget()
        timer = Timer()
        when {App.helper().HKSDKLogin("172.168.2.225", 8000, "admin", "dt8809020") != -1 && App.helper().HKSDKLogin("172.168.2.226", 8000, "admin", "dt8809020") != -1 -> {
                logIdOne = App.helper().HKSDKLogin("172.168.2.225", 8000, "admin", "dt8809020")
                logIdTwo = App.helper().HKSDKLogin("172.168.2.226", 8000, "admin", "dt8809020")
            } }
        val layoutparamsOne = LinearLayout.LayoutParams(resources.displayMetrics.widthPixels*2/3,resources.displayMetrics.heightPixels*2/3)
        val surfaceViewOne = SurfaceView(this)
        surfaceViewOne.setZOrderMediaOverlay(false)
        surfaceViewOne.setZOrderOnTop(false)
        surfaceViewOne.layoutParams = layoutparamsOne
        ts_container.addView(surfaceViewOne)
        val layoutparams = FrameLayout.LayoutParams(resources.displayMetrics.widthPixels/4,resources.displayMetrics.heightPixels/4,Gravity.BOTTOM)
        val surfaceViewTwo = SurfaceView(this)
        surfaceViewTwo.setZOrderOnTop(true)
        surfaceViewTwo.setZOrderMediaOverlay(true)
        surfaceViewTwo.layoutParams = layoutparams
        ts_container.addView(surfaceViewTwo)
        timer.schedule(object : TimerTask() {
            override fun run() {

                App.helper().HKSDKStartLivePreview(surfaceViewTwo,1,logIdOne)
                App.helper().HKSDKStartLivePreview(surfaceViewOne,1,logIdTwo)

            }
        },1000)




    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}


