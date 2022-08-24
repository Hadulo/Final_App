package com.example.agrilink

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.view.WindowManager
import java.lang.Exception
import io.agora.rtc.RtcEngine
import io.agora.rtc.IRtcEngineEventHandler

class CallActivity : AppCompatActivity() {
    // Kotlin
    // Fill the App ID of your project generated on Agora Console.
    private val APP_ID = "ecaef849c04c4652a081d079864f7c0e"
    // Fill the channel name.
    private val CHANNEL = ""
    // Fill the temp token generated on Agora Console.
    private val TOKEN = "ecaef849c04c4652a081d079864f7c0e"
    private var mRtcEngine: RtcEngine ?= null
    private val mRtcEventHandler = object : IRtcEngineEventHandler() {
    }
    private val PERMISSION_REQ_ID_RECORD_AUDIO = 22
    private val PERMISSION_REQ_ID_CAMERA = PERMISSION_REQ_ID_RECORD_AUDIO + 1

    private fun checkSelfPermission(permission: String, requestCode: Int): Boolean {
        if (ContextCompat.checkSelfPermission(this, permission) !=
            PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(permission),
                requestCode)
            return false
        }
        return true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO)) {
            initializeAndJoinChannel();
        }
    }
        private fun initializeAndJoinChannel() {
            try {
                mRtcEngine = RtcEngine.create(baseContext, APP_ID, mRtcEventHandler)
            } catch (e: Exception) {
            }
            mRtcEngine!!.joinChannel(TOKEN, CHANNEL, "", 0)
        }
    override fun onDestroy() {
        super.onDestroy()
        mRtcEngine?.leaveChannel()
        RtcEngine.destroy()
    }

}