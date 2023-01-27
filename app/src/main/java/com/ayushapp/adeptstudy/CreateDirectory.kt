package com.ayushapp.adeptstudy

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask


class CreateDirectory : AppCompatActivity() {

    private lateinit var etfolderName:EditText
    private lateinit var etgetDegreeLoc:EditText
    private lateinit var btcreateFolder:Button
    private lateinit var getName:String
    private lateinit var getDLocName:String

    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_directory)

        supportActionBar!!.title = "Create Folder"

        etgetDegreeLoc = findViewById(R.id.getDegreeLoc)
        etfolderName = findViewById(R.id.folderName)
        btcreateFolder = findViewById(R.id.createButton)


        btcreateFolder.setOnClickListener{

            getDLocName = etgetDegreeLoc.text.toString()
            getName = etfolderName.text.toString()

            storageReference = FirebaseStorage.getInstance().getReference(getDLocName+"/")
            databaseReference = FirebaseDatabase.getInstance().getReference(""+getDLocName)

            uploadProcess()
        }
    }

    private fun uploadProcess() {
        val folderReference:StorageReference = storageReference.child(getName+"/")
        val childReference:StorageReference = folderReference.child("file.txt")
        val data = "this is for check"

        var uploadTask:UploadTask = childReference.putBytes(data.toByteArray())
        uploadTask.addOnSuccessListener {

            val obj = FolderInfoModel(getName)
            databaseReference.child(databaseReference.push().key!!).setValue(obj)
            Toast.makeText(applicationContext,"Folder Created",Toast.LENGTH_LONG).show()

            etfolderName.setText("")

        }
    }
}