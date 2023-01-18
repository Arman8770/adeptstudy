package com.ayushapp.adeptstudy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

class ViewFolder : AppCompatActivity() {

    private var folderName: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyFolderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_folder)

        supportActionBar!!.hide()

        folderName = intent.getStringExtra("foldername").toString()
        recyclerView = findViewById(R.id.recycViewFolder)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val options: FirebaseRecyclerOptions<FileInfoModel> = FirebaseRecyclerOptions.Builder<FileInfoModel>()
            .setQuery(
                FirebaseDatabase.getInstance().getReference().child(folderName+""),
                FileInfoModel::class.java
            )
            .build()

        adapter = MyFolderAdapter(options)
        recyclerView.setAdapter(adapter)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.startListening()
    }
}