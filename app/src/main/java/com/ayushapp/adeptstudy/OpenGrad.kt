package com.ayushapp.adeptstudy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

var grad:String?=null
class OpenGrad : AppCompatActivity() {
    private lateinit var databasefolderRef: DatabaseReference
    private  lateinit var recview: RecyclerView
    private lateinit var folderArrayList: ArrayList<FolderInfoModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_grad)

        supportActionBar!!.hide()

            recview = findViewById(R.id.recyclerGrad)
            recview.layoutManager = LinearLayoutManager(this)
            recview.setHasFixedSize(true)

            grad = intent.getStringExtra("grad").toString()


            folderArrayList = arrayListOf()


            databasefolderRef = FirebaseDatabase.getInstance().getReference(""+grad)
            databasefolderRef.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (folderSnapshot in snapshot.children){
                            val folder = folderSnapshot.getValue(FolderInfoModel::class.java)
                            folderArrayList.add(folder!!)
                        }
                        recview.adapter = MyAdapterFolderShow(folderArrayList, grad!!)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }