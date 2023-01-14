package com.ayushapp.adeptstudy

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask


class CreateDirectory : AppCompatActivity() {

    private lateinit var etfolderName:EditText
    private lateinit var btcreateFolder:Button
    lateinit var getName:String

    private lateinit var storageReference: StorageReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_directory)

        supportActionBar!!.title = "Create Folder"

        etfolderName = findViewById(R.id.folderName)
        btcreateFolder = findViewById(R.id.createButton)


        btcreateFolder.setOnClickListener{

            getName = etfolderName.text.toString()

            storageReference = FirebaseStorage.getInstance().getReference(getName+"/")

            val child = storageReference.child("text.txt")
            val data = "This is demo data...."
            val upload:UploadTask = child.putBytes(data.toByteArray())

            upload.addOnSuccessListener {
                Toast.makeText(this,"Folder Created",Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}