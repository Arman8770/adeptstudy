package com.ayushapp.adeptstudy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ViewFolder : AppCompatActivity() {

    private var folderName: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_folder)

        supportActionBar!!.hide()

        folderName= intent.getStringExtra("foldername").toString()






    }
}