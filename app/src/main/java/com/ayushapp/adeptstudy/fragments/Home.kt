package com.ayushapp.adeptstudy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayushapp.adeptstudy.FolderInfoModel
import com.ayushapp.adeptstudy.MyAdapter
import com.ayushapp.adeptstudy.R
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*


class Home : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private  lateinit var recview: RecyclerView
    private lateinit var adapter: MyAdapter
    private lateinit var folderArrayList: ArrayList<FolderInfoModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        recview = view.findViewById(R.id.recview)
        recview.layoutManager = LinearLayoutManager(context)
        recview.setHasFixedSize(true)

        folderArrayList = arrayListOf()

        databaseReference = FirebaseDatabase.getInstance().getReference("FolderLocation")
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (folderSnapshot in snapshot.children){
                        val folder = folderSnapshot.getValue(FolderInfoModel::class.java)
                        folderArrayList.add(folder!!)
                    }
                    recview.adapter = MyAdapter(folderArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show()
            }
        })

        return (view)
    }

}