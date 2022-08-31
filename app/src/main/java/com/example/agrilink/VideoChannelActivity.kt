package com.example.agrilink

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import io.agora.rtc.Constants
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import io.agora.rtc.video.VideoCanvas

class VideoChannelActivity : AppCompatActivity() {

    private var mRtcEngine: RtcEngine ? = null
    private var channelName: String ? = null
    private var userRole = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_activity)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        channelName = intent.getStringExtra("Channel Name")
        userRole = intent.getIntExtra("User Role", -1)
        intentAngoraEngineAndJoinChannel()
    }

    override fun onDestroy() {
        super.onDestroy()
        mRtcEngine!!.leaveChannel()
        RtcEngine.destroy()
        mRtcEngine = null
    }

    private fun intentAngoraEngineAndJoinChannel(){
        initializeAngoraEgine()
        mRtcEngine!!.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING)
        mRtcEngine!!.setClientRole(userRole)

        mRtcEngine!!.enableVideo()
        if (userRole == 1){
            setupLocalVideo()
        }else{
            val localVideoCanvas = findViewById<View>(R.id.local_video_view_container)
            localVideoCanvas?.isVisible = false
        }
        joinChannel()
    }

    private val mRtcEventHandler : IRtcEngineEventHandler = object : IRtcEngineEventHandler(){
        override fun onUserJoined(uid: Int, elapsed: Int) {
           runOnUiThread{setupRrmoteVideo(uid)}
        }

        override fun onUserOffline(uid: Int, reason: Int){
            runOnUiThread { onRemoteUserLeft() }
        }

        override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
            runOnUiThread { println("Join channel success : $uid") }
        }
    }

    private fun initializeAngoraEgine(){
        try {
            mRtcEngine = RtcEngine.create(baseContext, APP_ID, mRtcEventHandler)
        }catch (e:Exception){
            println("Exception: ${e.message}")
        }
    }

    private fun setupLocalVideo(){
        val container = findViewById<View>(R.id.local_video_view_container) as FrameLayout
        val surfaceView = RtcEngine.CreateRendererView(baseContext)
        surfaceView.setZOrderMediaOverlay(true)
        container.addView(surfaceView)
        mRtcEngine!!.setupLocalVideo(VideoCanvas(surfaceView , VideoCanvas.RENDER_MODE_FIT, 0))
    }

    private fun joinChannel(){
        mRtcEngine!!.joinChannel(token, channelName, null, 0)
    }

    private fun setupRrmoteVideo(uid: Int){
        val container = findViewById<View>(R.id.remote_video_view_container) as FrameLayout

        if (container.childCount>= 1){
            return
        }

        val surfaceView = RtcEngine.CreateRendererView(baseContext)
        container.addView(surfaceView)
        mRtcEngine!!.setupRemoteVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid))
        surfaceView.tag = uid
    }
    private fun onRemoteUserLeft() {
        val container = findViewById<View>(R.id.remote_video_view_container) as FrameLayout
        container.removeAllViews()
    }
    fun onLocalAudioMuteClick(view: View) {
        val iv = view as ImageView
        if (iv.isSelected){
            iv.isSelected = false
            iv.clearColorFilter()
        }else{
            iv.isSelected = true
            iv.setColorFilter(resources.getColor(R.color.agora_theme),PorterDuff.Mode.MULTIPLY)
        }
        mRtcEngine!!.muteLocalAudioStream(iv.isSelected)
    }
    fun onSwitchCameraClicked(view: View){
        mRtcEngine!!.switchCamera()
    }
    fun onEndCallClicked(view: View){
        finish()
    }
}