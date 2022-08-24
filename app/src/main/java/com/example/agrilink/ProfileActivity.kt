package com.example.agrilink

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AlertDialogLayout
import com.example.agrilink.databinding.*
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.lang.ref.Reference

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imageUri : Uri
    private lateinit var dialog: Dialog
    private lateinit var dialog2:AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog2 = AlertDialog.Builder(this)
            .setMessage("Updating Profile...")
            .setCancelable(false)

        binding.userImage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        binding.saveBtn.setOnClickListener {
            showProgressBar()
            val name = binding.name.text.toString()
            val email = binding.email.text.toString()
            val phone = binding.phone.text.toString()

            val user = User(name,email,phone)
            if (uid != null){
                databaseReference.child(uid).setValue(user).addOnCompleteListener {

                    if (it.isSuccessful){
                        uploadProfilePic()
                    }else{
                        hideProgressBar()
                        Toast.makeText(this@ProfileActivity, "Failed to update profile",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null){
            if (data.data != null){
                imageUri = data.data!!

                binding.userImage.setImageURI(imageUri)
            }
        }
    }

    private fun uploadProfilePic() {
        imageUri = Uri.parse("android.resource://$packageName/${R.layout.activity_profile}")
        storageReference = FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnSuccessListener {
            hideProgressBar()
            Toast.makeText(this@ProfileActivity, "Profile successfully updated", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            hideProgressBar()
            Toast.makeText(this@ProfileActivity, "Failed to update profile", Toast.LENGTH_SHORT).show()
        }
    }
    private fun showProgressBar(){
        dialog = Dialog(this@ProfileActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun hideProgressBar(){

        dialog.dismiss()
    }
}