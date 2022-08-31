package com.example.agrilink

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class PostActivity : AppCompatActivity() {
    lateinit var filepath :Uri
    var buttonChoose:Button ?= null
    var buttonUpload:Button ?= null
    var imageView:ImageView ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        imageView = findViewById(R.id.mImg)
        buttonChoose = findViewById(R.id.button)
        buttonUpload = findViewById(R.id.button2)

        buttonChoose!!.setOnClickListener {
            startFileChooser()
        }
        buttonUpload!!.setOnClickListener {
            upLoadFile()
        }


    }

    private fun upLoadFile() {
        if (filepath!=null){
            val pd = ProgressDialog(this)
            pd.setTitle("Uploading")
            pd.show()
            val imageRef:StorageReference = FirebaseStorage.getInstance().reference.child("images/pic.jpg")
            imageRef.putFile(filepath)
                .addOnSuccessListener {p0 ->
                    Toast.makeText(applicationContext, "File is Uploaded",Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {p0 ->
                    pd.dismiss()
                    Toast.makeText(applicationContext,p0.message, Toast.LENGTH_LONG).show()
                }
                .addOnProgressListener {p0 ->
                        val progress :Double = (100.0 * p0.bytesTransferred) / p0.totalByteCount
                        pd.setMessage("Uploaded ${progress.toInt()}%")
                }
        }
    }

    private fun startFileChooser() {
        val i = Intent()
        i .setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i, "Chose Picture"),111)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==111 && resultCode == Activity.RESULT_OK && data != null){
            filepath = data.data!!
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,filepath)
            imageView?.setImageBitmap(bitmap)
        }
    }
}