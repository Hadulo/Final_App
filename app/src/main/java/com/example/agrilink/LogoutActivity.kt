package com.example.agrilink

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LogoutActivity : AppCompatActivity() {
    lateinit var buttonLogOut:Button
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize firebase auth object
        auth = FirebaseAuth.getInstance()

        buttonLogOut = findViewById(R.id.mbtnSignOut)
        buttonLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(
                applicationContext, "Log Out Successful",
                Toast.LENGTH_LONG).show()
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}