package com.ayushapp.adeptstudy

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.material.bottomnavigation.BottomNavigationView


@Suppress("DEPRECATION")
class UserLoginSuccessful : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_user_successful)
        supportActionBar!!.hide()

        val account = intent.getParcelableExtra<GoogleSignInAccount>("account")
        personName = account!!.displayName
        personEmail = account.email
        photoURL = account.photoUrl
        personId = account.id

        val bottomNavigationuser = findViewById<BottomNavigationView>(R.id.bottomNavigationViewUser)
        val fragmentContaineruser = findViewById<View>(R.id.frameLayoutContainerUser)
        bottomNavigationuser.setupWithNavController(fragmentContaineruser.findNavController())



    }
}