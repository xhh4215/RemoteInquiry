package com.remote.hklibrary.interfaces

 import android.view.SurfaceView

interface HKINetHelper {

    fun   HKSDKInit(surface:SurfaceView):Boolean
    fun   HKSDKClean()
    fun   HKSDKLogin(ip:String,port:Int,username:String,password:String):Int
    fun   HKSDKLogout(userId:Int):Boolean
    fun   HKSDKStartLivePreview(surface: SurfaceView,channel:Int,log:Int):Int
    fun   HKSDKStopLivePreview(logout:Int)




}