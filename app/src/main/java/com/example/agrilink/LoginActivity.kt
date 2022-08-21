package com.example.agrilink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    var editTextEmail: EditText?= null
    var editTextPassword: EditText?= null
    var buttonLogin: Button?= null
    var textViewRegister: TextView?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        editTextEmail = findViewById(R.id.mEdtEmail)
        editTextPassword = findViewById(R.id.mEdtPassword)
        buttonLogin = findViewById(R.id.mBtnLogin)
        textViewRegister = findViewById(R.id.mTvRegisterH)
        firebaseAuth = FirebaseAuth.getInstance()

        buttonLogin!!.setOnClickListener {
            val userEmail = editTextEmail!!.text.toString().trim()
            val userPassword = editTextPassword!!.text.toString().trim()

            //Check if the user has submitted empty fields
            if (userPassword.length < 6) {
                editTextPassword!!.setError("Password must be  6 Characters and above")
                editTextPassword!!.requestFocus()
            }
            if (userEmail.isEmpty()){
                editTextEmail!!.setError("Please fill in this field!!!")
                editTextEmail!!.requestFocus()
            }else if (userPassword.isEmpty()){
                editTextPassword!!.setError("Please fill in this field!!!")
                editTextPassword!!.requestFocus()
            }else{
                firebaseAuth.createUserWithEmailAndPassword(userEmail ,userPassword).addOnCompleteListener {
                    if (it.isSuccessful){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                        Toast.makeText(
                            baseContext, "Success",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else {
                        //If fails to display message to user

                        Toast.makeText(
                            baseContext, "Authentification Failed.",
                            Toast.LENGTH_SHORT
                        ).show()
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

}
