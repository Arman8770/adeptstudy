package com.ayushapp.adeptstudy

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


class UploadFile : AppCompatActivity(), PermissionListener {

    private lateinit var browseFile: ImageView
    private lateinit var cancelFile: ImageView
    private lateinit var etFileName: EditText
    private lateinit var etFolderLocation: EditText
    private lateinit var btuploadFile: Button

    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference

    private lateinit var filepath:Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_file)

        //supportActionBar!!.title = "Upload"
        supportActionBar!!.hide()

        //find id
        browseFile = findViewById(R.id.browseFile)
        cancelFile = findViewById(R.id.cancelFile)
        etFileName = findViewById(R.id.fileName)
        etFolderLocation = findViewById(R.id.chooseFolder)
        btuploadFile = findViewById(R.id.uploadFile)

        cancelFile.visibility = View.INVISIBLE

        //firebase get storage and database reference
        storageReference = FirebaseStorage.getInstance().getReference()
        databaseReference = FirebaseDatabase.getInstance().getReference("mydocuments")

        cancelFile.setOnClickListener {
            browseFile.setImageResource(R.drawable.ic_cloud_upload)
            cancelFile.visibility = View.INVISIBLE

        }

        browseFile.setOnClickListener {
            browse()
        }

        btuploadFile.setOnClickListener{
            uploadProcess(filepath)
        }
    }



    // this method is used to take file from file manager
    private fun browse() {
        Dexter.withContext(getApplicationContext())
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(this)
            .check()

    }

    //this method for take permission and get file from mobile storage
    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Pdf Files"),101)
    }

    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
        TODO("Not yet implemented")
    }

    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {
        p1!!.continuePermissionRequest()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 101 && resultCode == RESULT_OK){
            filepath = data!!.data!!
            cancelFile.visibility = View.VISIBLE
            browseFile.setImageResource(R.drawable.pdf)
        }
    }



    //this method for upload files on firebase storage
    private fun uploadProcess(filepath: Uri) {

        val pd:ProgressDialog = ProgressDialog(this)
        pd.setTitle("File Uploading.....!!!")
        pd.show()

        val childReference:StorageReference = storageReference.child("Upload/"+System.currentTimeMillis()+".pdf")
        childReference.putFile(filepath)
            .addOnSuccessListener {
                childReference.downloadUrl.addOnSuccessListener {
                    val obj:FileInfoModel = FileInfoModel(etFileName.text.toString(),filepath.toString())
                    databaseReference.child(databaseReference.push().key!!).setValue(obj)

                    pd.dismiss()
                    Toast.makeText(applicationContext,"File Uploads",Toast.LENGTH_LONG).show()

                    cancelFile.visibility = View.INVISIBLE
                    browseFile.setImageResource(R.drawable.ic_cloud_upload)
                    etFileName.setText("")
                }
            }
            .addOnProgressListener {
                val percent: Long = (100*it.bytesTransferred)/it.totalByteCount
                pd.setMessage("Upload :"+percent.toInt()+"%")


            }

    }
}