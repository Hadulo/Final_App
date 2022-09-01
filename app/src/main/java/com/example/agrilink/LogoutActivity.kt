package com.example.agrilink

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LogoutActivity : AppCompatActivity() {
    lateinit var buttonSignOut:Button
    lateinit var auth: FirebaseAuth
    lateinit var tvUserEmail:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)
        tvUserEmail = findViewById(R.id.tvUserEmail)
        buttonSignOut = findViewById(R.id.mbtnSignOut)

        //initialize firebase auth object
        auth = FirebaseAuth.getInstance()

       if (auth.currentUser != null){
           auth.currentUser?.let {

               tvUserEmail.text = it.email

           }
       }

        buttonSignOut.setOnClickListener{
            auth.signOut()
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
        }
        finish()
    }
}