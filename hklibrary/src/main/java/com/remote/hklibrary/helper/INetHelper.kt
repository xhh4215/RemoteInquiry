package com.remote.hklibrary.helper
import android.graphics.PixelFormat
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.hikvision.netsdk.HCNetSDK
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO
import com.remote.hklibrary.interfaces.HKINetHelper
import com.remote.hklibrary.utils.HCNetSDKJNAInstance
import org.MediaPlayer.PlayM4.Player
import android.R.attr.bitmap
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.RectF
import android.R
import android.graphics.BitmapFactory



class INetHelper : HKINetHelper {
    private val TAG = "HKINetHelper"
    //这是播放的端口
    private var hkPlayPort = -1
    private var hkLogId = -1
    private var hkPlayId =-1

    override fun HKSDKInit(surface: SurfaceView): Boolean {
        if (!onInit()) {
            return false
        }
//        onSurfaceInit(surface)
        return true
    }
    /***
     * sdk初始化的准备
     */
    fun onInit(): Boolean {
        if (!HCNetSDK.getInstance().NET_DVR_Init()) {
            Log.e(TAG, "HCNetSDK Init is Failed"+HCNetSDK.getInstance().NET_DVR_GetLastError())
            return false
        }
        HCNetSDK.getInstance().NET_DVR_SetLogToFile(3, "/sdkLog", true)
        Log.e(TAG, "HCNetSDK Init is Success")
        return true
    }
    /***
     * @return -1 表示失败，其他值表示返回的用户 ID 值。该用户 ID 具有唯一性，后续对设备的操作都需要通 过此 ID 实现。
     * 接口返回失败请调用 NET_DVR_GetLastError 获取错误码，通过错误码判断出错原 因
     */
    override fun HKSDKLogin(ip: String, port: Int, username: String, password: String): Int {
        var deviceInfo = NET_DVR_DEVICEINFO_V30()
        if (deviceInfo==null){
            Log.e(TAG, "HCNetSDK deviceInfo create Failed"+HCNetSDK.getInstance().NET_DVR_GetLastError())
            return -1
        }
        // 获取后续操作的唯一id   -1 标识登录失败  7
        hkLogId =  HCNetSDK.getInstance().NET_DVR_Login_V30(ip,port,username,password,deviceInfo)
        if (hkLogId<0){
            Log.e(TAG, "HCNetSDK Login Failed"+HCNetSDK.getInstance().NET_DVR_GetLastError())
            return -1
        }
        Log.e(TAG, "HCNetSDK Login Success")
        return hkLogId
    }
    /***
     * TRUE 表示成功，FALSE 表示失败。接口返回失败请调用 NET_DVR_GetLastError 获取错误码，通 过错误码判断出错原因。
     */
    override fun HKSDKLogout(userId: Int): Boolean {
        if ( HCNetSDK.getInstance().NET_DVR_Logout_V30(userId)){
            hkLogId=-1
            return true
        }
        Log.e(TAG, "HCNetSDK Logout Failed"+HCNetSDK.getInstance().NET_DVR_GetLastError())
        return false
    }

    /***
     * 开启实时预览
     * @param surface 播放的窗口
     * @param channel 播放的通道
     */
    override fun HKSDKStartLivePreview(surface: SurfaceView, channel: Int,logId:Int):Int {
        onSurfaceInit(surface)
        val previewInfo = NET_DVR_PREVIEWINFO()
        previewInfo.lChannel = channel
        previewInfo.dwStreamType = 0 // substream
        previewInfo.bBlocked = 1
        previewInfo.hHwnd = surface.holder
        hkPlayId = HCNetSDK.getInstance().NET_DVR_RealPlay_V40(logId, previewInfo, null)
        if (hkPlayId < 0) {
            Log.e(TAG, "NET_DVR_RealPlay is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError())
            return hkPlayId
        }

        var bRet = HCNetSDKJNAInstance.getInstance().NET_DVR_OpenSound(hkPlayId);
        if (bRet) {
            Log.e(TAG, "NET_DVR_OpenSound Succ!");
        }
        Log.i(TAG, "NetSdk Play sucess ***********************3***************************")
        return hkPlayId
    }

    /***
     * 关闭实时预览
     */
    override fun HKSDKStopLivePreview(logoutId:Int) {
        if (logoutId < 0) {
            Log.e(TAG, "m_iPlayID < 0")
            return
        }

        if (HCNetSDKJNAInstance.getInstance().NET_DVR_CloseSound()) {
            Log.e(TAG, "NET_DVR_CloseSound Succ!")
        }

        // net sdk stop preview
        if (!HCNetSDK.getInstance().NET_DVR_StopRealPlay(logoutId)) {
            Log.e(
                TAG,
                "StopRealPlay is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError()
            )
            return
        }
        Log.i(TAG, "NET_DVR_StopRealPlay succ")
        hkPlayId = -1
    }

    /***
     * 将播放的控件和surfaceview绑定
     */
    fun onSurfaceInit(surface: SurfaceView){
        val holder= surface.holder
        holder.setFormat(PixelFormat.OPAQUE)
        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder?) {
                if (hkPlayPort==-1){
                    return
                }

                if(holder!!.surface.isValid){
                    if (!Player.getInstance().setVideoWindow(hkPlayPort,0,holder)){
                        Log.e(TAG, "Player setVideoWindow failed!")
                    }
                }
            }
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
            }
            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                if (hkPlayPort==-1){
                    return
                }
                if(holder!!.surface.isValid){
                    if (!Player.getInstance().setVideoWindow(hkPlayPort,0,holder)){
                        Log.e(TAG, "Player setVideoWindow failed!")
                    }
                }
            }
        })
    }
override fun HKSDKClean() {
    when (HCNetSDK.getInstance().NET_DVR_Cleanup()) {
        false -> Log.e(TAG, "HCNetSDK Clear is Failed"+HCNetSDK.getInstance().NET_DVR_GetLastError())
        true -> Log.e(TAG, "HCNetSDK Clean is Success")
    }
}
}