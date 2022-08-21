package com.example.agrilink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val videoCall = findViewById<ImageView>(R.id.myImageVideo)

        videoCall.setOnClickListener {
            val intent = Intent(this, VideoChatActivity::class.java)
            startActivity(intent)
        }

        val voiceCall = findViewById<ImageView>(R.id.imageVoiceCall)
        voiceCall.setOnClickListener {
            val intent = Intent(this, CallActivity::class.java)
            startActivity(intent)
        }


    }
}