package com.ayushapp.adeptstudy.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ayushapp.adeptstudy.*
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso


@Suppress("DEPRECATION")
class Profile : Fragment() {
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view:View = inflater.inflate(R.layout.fragment_profile, container, false)
        val name:TextView=view.findViewById(R.id.profileName)
        val email:TextView = view.findViewById(R.id.email)
        val photo:ImageView = view.findViewById(R.id.profileImage)
        photo.isDrawingCacheEnabled = true
        photo.buildDrawingCache()

        //set profile Information
        name.text = personName
        email.text = personEmail
        Picasso.get().load(photoURL).into(photo)



        auth = FirebaseAuth.getInstance()
        val signout = view.findViewById<MaterialButton>(R.id.gSignOutBtn)

        signout.setOnClickListener{

            auth.signOut()
            activity?.let{
                val intent = Intent (it, Login::class.java)
                it.startActivity(intent)
            }
        }

        return(view)
    }



}
