package com.ayushapp.adeptstudy

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class MyFolderAdapter(options: FirebaseRecyclerOptions<FileInfoModel>) :
    FirebaseRecyclerAdapter<FileInfoModel, MyFolderAdapter.MyFolderViewHolder>(options) {

    class MyFolderViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var pdfName =  itemView.findViewById<TextView>(R.id.pdfName)
        var pdftouch = itemView.findViewById<RelativeLayout>(R.id.pdftouchUp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFolderViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.single_row_design_pdf,parent,false)
        return MyFolderViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyFolderViewHolder, position: Int, model: FileInfoModel) {
        holder.pdfName.setText(model.filename)

        holder.pdftouch.setOnClickListener(){
            val intent = Intent(holder.pdftouch.context,ViewPdf::class.java)
            intent.putExtra("filename",model.filename)
            intent.putExtra("fileurl",model.fileurl)

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            holder.pdftouch.context.startActivity(intent)
        }
    }
}