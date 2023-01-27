package com.ayushapp.adeptstudy

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.firestore.FirebaseFirestore

@Suppress("DEPRECATION")
class AdminPermission : AppCompatActivity() {

    private lateinit var dbFirestore:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_permission)

        supportActionBar!!.hide()

        val rightTick =  findViewById<LottieAnimationView>(R.id.animationRightTick)
        Handler(Looper.getMainLooper()).postDelayed({
            rightTick.visibility = View.VISIBLE
            rightTick.playAnimation()
        },1000)

        val account = intent.getParcelableExtra<GoogleSignInAccount>("account")
        personName = account!!.displayName
        personEmail = account.email
        photoURL = account.photoUrl
        personId = account.id


        dbFirestore = FirebaseFirestore.getInstance()

        insertdata()
    }

    private fun insertdata() {
        // Create a new user with a first and last name
        val user = hashMapOf(
            "name" to personName,
            "email" to personEmail,
            "photo" to photoURL,
            "access" to false
        )

// Add a new document with a generated ID
        dbFirestore.collection("users")
            .document(""+ personEmail)
            .set(user)
            .addOnSuccessListener {
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}