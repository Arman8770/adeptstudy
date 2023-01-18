package com.ayushapp.adeptstudy

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

var personName:String? = null
var personEmail:String? = null
var personId:String? = null
var photoURL:Uri?=null

@Suppress("DEPRECATION")
class LoginAdminSuccessful : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_admin_successful)

        supportActionBar!!.hide()

        //Google get profile Information
        val account = intent.getParcelableExtra<GoogleSignInAccount>("account")
        personName = account!!.displayName
        personEmail = account.email
        photoURL = account.photoUrl
        personId = account.id



        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val fragmentContainer = findViewById<View>(R.id.frameLayoutContainer)
        bottomNavigation.setupWithNavController(fragmentContainer.findNavController())


        //floating button click option
        val clickfab=findViewById<FloatingActionButton>(R.id.floatingActionButton)

        clickfab.setOnClickListener{
            clickOnFloatButton()
        }
    }

    private fun clickOnFloatButton() {
        val view:View = layoutInflater.inflate(R.layout.item_bottom_sheet,null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        dialog.show()

        val createFolder = view.findViewById<TextView>(R.id.createFolder)
        val uploadFile = view.findViewById<TextView>(R.id.uploadFile)

        createFolder.setOnClickListener {
            val intent = Intent(this,CreateDirectory::class.java)
            startActivity(intent)
        }
        uploadFile.setOnClickListener {
            val intent = Intent(this,UploadFile::class.java)
            startActivity(intent)
        }
    }
}


