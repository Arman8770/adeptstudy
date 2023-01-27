package com.ayushapp.adeptstudy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ayushapp.adeptstudy.*
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlin.concurrent.thread


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
            thread {
                while (true) {
                    Thread.sleep(500)
                    kotlin.run { deleteAppData() }
                }
            }

            auth.signOut()
//            activity?.let{
//                val intent = Intent (it, Login::class.java)
//                it.startActivity(intent)
//            }
        }

        return(view)
    }
    private fun deleteAppData() {
        try {
            // clearing app data
            val packageName = context?.packageName
            val runtime = Runtime.getRuntime()
            runtime.exec("pm clear $packageName")
            Toast.makeText(context,"Sign Out Successfully",Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}
