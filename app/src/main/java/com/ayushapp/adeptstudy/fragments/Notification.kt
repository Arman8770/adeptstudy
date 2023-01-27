package com.ayushapp.adeptstudy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayushapp.adeptstudy.MyUserAdapter
import com.ayushapp.adeptstudy.R
import com.ayushapp.adeptstudy.UserInfoModel
import com.google.firebase.firestore.FirebaseFirestore

class Notification : Fragment() {
    private lateinit var recycleUser:RecyclerView
    private lateinit var userList: ArrayList<UserInfoModel>
    private lateinit var db:FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notification, container, false)

        recycleUser = view.findViewById(R.id.recycleNotification)
        recycleUser.layoutManager = LinearLayoutManager(context)
        recycleUser.setHasFixedSize(true)

        //initialize array list
        userList = arrayListOf()

        db = FirebaseFirestore.getInstance()

        db.collection("users")
            .get().addOnSuccessListener {
                for (document in it.documents) {
                    val obj:UserInfoModel? = document.toObject(UserInfoModel::class.java)
                    userList.add(obj!!)
                }
                recycleUser.adapter = MyUserAdapter(userList)
                }
                .addOnFailureListener {
                    Toast.makeText(context, it.toString(),Toast.LENGTH_SHORT).show()
                }
        return view

    }
}