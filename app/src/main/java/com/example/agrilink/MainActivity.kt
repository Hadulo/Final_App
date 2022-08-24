package com.example.agrilink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val signOut = findViewById<Button>(R.id.mbnSubmit)
        firebaseAuth = FirebaseAuth.getInstance()
        signOut.setOnClickListener {
           firebaseAuth.signOut()
           startActivity(
               Intent(
                   this,LoginActivity::class.java
               )
           )
            finish()
        }


        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

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
        val profile = findViewById<Button>(R.id.profileBtn)
        profile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }


    }
}