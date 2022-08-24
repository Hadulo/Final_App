package com.example.agrilink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.core.app.ActivityCompat
import java.util.jar.Manifest

class VideoChatActivity : AppCompatActivity() {

    var userRole = 0
    var btnEndChat:Button ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.channel_layout)
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        btnEndChat = findViewById(R.id.mbnSubmit)
        btnEndChat!!.setOnClickListener {
            val intent = Intent(applicationContext, VideoChannelActivity::class.java)
            startActivity(intent)
        }

        requestPermission()

    }
    private fun requestPermission() {
      ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.RECORD_AUDIO),22)
    }

    fun onSubmit(view: TextView){
        val channelName = findViewById<View>(R.id.channel) as EditText
        val userRadioButton = findViewById<View>(R.id.radioGroup) as RadioGroup

        val checked = userRadioButton.checkedRadioButtonId
        val audienceButtonId = findViewById<View>(R.id.audience) as RadioButton

        userRole = if (checked == audienceButtonId.id){
            0
        }else{
            1
        }

        val intent = Intent(this, VideoChannelActivity::class.java)
        intent.putExtra("Channel Name", channelName.text.toString())
        intent.putExtra("User Role", userRole)
        startActivity(intent)
    }

    fun onLocalAudioMuteClick(view: View) {}
    fun onEndCallClicked(view: View) {}
    fun onSwitchCameraClicked(view: View) {}

}