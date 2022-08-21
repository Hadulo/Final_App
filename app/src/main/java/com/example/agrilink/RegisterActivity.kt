package com.example.agrilink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    var editTextName: EditText?= null
    var editTextEmail: EditText?= null
    var editTextPassword: EditText?= null
    var editTextPhone: EditText?= null
    var buttonRegister: Button?= null
    var progressBar: ProgressBar?= null
    var textViewLogin: TextView?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        editTextName = findViewById(R.id.mEdtFullName)
        editTextEmail = findViewById(R.id.mEdtEmail)
        editTextPassword = findViewById(R.id.mEdtPassword)
        editTextPhone = findViewById(R.id.mEdtPhone)
        buttonRegister  = findViewById(R.id.mBtnRegister)
        progressBar = findViewById(R.id.progressBar)
        textViewLogin = findViewById(R.id.mTvLoginH)
        firebaseAuth = FirebaseAuth.getInstance()
        buttonRegister!!.setOnClickListener {
            val userName = editTextName!!.text.toString().trim()
            val userEmail = editTextEmail!!.text.toString().trim()
            val userPassword = editTextPassword!!.text.toString().trim()
            val userPhone = editTextPhone!!.text.toString().trim()

            //Check if the user has submitted empty fields
            if (userPassword.length < 6){
                editTextPassword!!.setError("Password must be  6 Characters and above")
                editTextPassword!!.requestFocus()
            }
            if (userName.isEmpty()){
                editTextName!!.setError("Please fill in this field!!!")
                editTextName!!.requestFocus()
            }else if (userEmail.isEmpty()){
                editTextEmail!!.setError("Please fill in this field!!!")
                editTextEmail!!.requestFocus()
            }else if (userPassword.isEmpty()){
                editTextPassword!!.setError("Please fill in this field!!!")
                editTextPassword!!.requestFocus()
            }else if (userPhone.isEmpty()){
                editTextPhone!!.setError("Please fill in this field!!!")
                editTextPhone!!.requestFocus()
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
        textViewLogin!!.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }



    }


}
