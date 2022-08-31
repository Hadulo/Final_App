package com.example.agrilink

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private var editTextEmail: EditText?= null
    private var editTextPassword: EditText?= null
    private var buttonRegister: Button?= null
    private var textViewLogin: TextView?= null
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.hide()
        editTextEmail = findViewById(R.id.mEdtEmail)
        editTextPassword = findViewById(R.id.mEdtPassword)
        buttonRegister  = findViewById(R.id.mBtnRegister)
        textViewLogin = findViewById(R.id.mTvLoginH)
        firebaseAuth = FirebaseAuth.getInstance()
        buttonRegister!!.setOnClickListener {
            val userEmail = editTextEmail!!.text.toString().trim()
            val userPassword = editTextPassword!!.text.toString().trim()


            //Check if the user has submitted empty fields
            if (userPassword.length < 6){
                editTextPassword!!.error = "Password must be  6 Characters and above"
                editTextPassword!!.requestFocus()

           }else if (userEmail.isEmpty()){
                editTextEmail!!.error = "Please fill in this field!!!"
                editTextEmail!!.requestFocus()
            }else if (userPassword.isEmpty()){
                editTextPassword!!.error = "Please fill in this field!!!"
                editTextPassword!!.requestFocus()
           }else{
                firebaseAuth.createUserWithEmailAndPassword(userEmail ,userPassword).addOnCompleteListener {
                    if (it.isSuccessful){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                        Toast.makeText(
                            baseContext, "Success",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else {
                        //If fails to display message to user

                        Toast.makeText(
                            baseContext, "Authentication Failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }





        }
        textViewLogin!!.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }



    }




}
