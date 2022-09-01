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

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private var editTextEmail: EditText?= null
    private var editTextPassword: EditText?= null
    private var buttonLogin: Button?= null
    private var textViewRegister: TextView?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()



        editTextEmail = findViewById(R.id.mEdtEmail)
        editTextPassword = findViewById(R.id.mEdtPassword)
        buttonLogin = findViewById(R.id.mBtnLogin)
        textViewRegister = findViewById(R.id.mTvRegisterH)
        firebaseAuth = FirebaseAuth.getInstance()
        checkIfUserIsLogged()


        buttonLogin!!.setOnClickListener {
            val userEmail = editTextEmail!!.text.toString().trim()
            val userPassword = editTextPassword!!.text.toString().trim()

            FirebaseAuth.getInstance().signInWithEmailAndPassword(userEmail,userPassword)

            //Check if the user has submitted empty fields
            if (userPassword.length < 6) {
                editTextPassword!!.error = "Password must be  6 Characters and above"
                editTextPassword!!.requestFocus()
            }
            if (userEmail.isEmpty()){
                editTextEmail!!.error = "Please fill in this field!!!"
                editTextEmail!!.requestFocus()
            }else if (userPassword.isEmpty()){
                editTextPassword!!.error = "Please fill in this field!!!"
                editTextPassword!!.requestFocus()
            }else{
                firebaseAuth.createUserWithEmailAndPassword(userEmail ,userPassword).addOnCompleteListener {
                    if (it.isSuccessful){


                        Toast.makeText(
                            this, "Success",
                            Toast.LENGTH_SHORT
                        ).show()

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else {
                        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener {mTask->

                                if (mTask.isSuccessful){
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }else{

                                    Toast.makeText(
                                        baseContext, "Authentication Failed.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()
                }

            }

        }
        textViewRegister!!.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
    private fun checkIfUserIsLogged(){

        if (firebaseAuth.currentUser !=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }





}
