package com.ayushapp.adeptstudy

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class MyUserAdapter(private val userList: ArrayList<UserInfoModel>): RecyclerView.Adapter<MyUserAdapter.MyUserViewAdapter>() {
    class MyUserViewAdapter(itemView:View): RecyclerView.ViewHolder(itemView){

        var userActivate: Button = itemView.findViewById(R.id.userActivate)
        var userEmail: TextView = itemView.findViewById(R.id.userEmail)
        var userName: TextView = itemView.findViewById(R.id.userName)
        var userPhoto: ImageView = itemView.findViewById(R.id.userPhoto)

        private var dabRef = FirebaseFirestore.getInstance()

        fun getUserStatus(postkey: String?) {
            dabRef.collection("users").document(""+postkey)
                .addSnapshotListener { value, error ->
                    if(value!!.getBoolean("access")==true){
                        userActivate.setText(R.string.deactivate)
                    }
                    else{
                        userActivate.setText(R.string.activate)
                    }
                }
        }
    }


    private var testClick = false
    private var dataRef=FirebaseFirestore.getInstance()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyUserViewAdapter {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.single_notification_design,parent,false)
        return MyUserViewAdapter(itemView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: MyUserViewAdapter, position: Int) {

        holder.userEmail.text = userList[position].email
        holder.userName.text = userList[position].name
        val uri: Uri = Uri.parse(userList[position].photo)
        Picasso.get().load(uri).into(holder.userPhoto)

        val postkey = userList[position].email

        holder.getUserStatus(postkey)



        val checkInRef = dataRef.collection("users").document(""+postkey)
        holder.userActivate.setOnClickListener{
            testClick = true
                checkInRef.addSnapshotListener { value, error ->
                    if(testClick==true && value!!.getBoolean("access")==true){
                        val user = hashMapOf(
                            "name" to userList[position].name,
                            "email" to userList[position].email,
                            "photo" to userList[position].photo,
                            "access" to false
                        )
                        checkInRef.set(user)
                        testClick=false
                    }
                    else if (testClick==true && value!!.getBoolean("access")==false){
                        val user = hashMapOf(
                            "name" to userList[position].name,
                            "email" to userList[position].email,
                            "photo" to userList[position].photo,
                            "access" to true
                        )
                        checkInRef.set(user)
                        testClick=false
                    }
                }
        }
    }
}


