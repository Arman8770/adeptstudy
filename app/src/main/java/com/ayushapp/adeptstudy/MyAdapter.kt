package com.ayushapp.adeptstudy

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val folder:ArrayList<FolderInfoModel>):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var relativeTouch = itemView.findViewById<RelativeLayout>(R.id.touchUp)
        var header = itemView.findViewById<TextView>(R.id.header)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView  = LayoutInflater.from(parent.context).inflate(R.layout.single_row_design,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return folder.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = folder[position]

        holder.header.text = currentItem.foldername
        holder.relativeTouch.setOnClickListener {
            val intent = Intent(holder.relativeTouch.context,ViewFolder::class.java)
            intent.putExtra("foldername", currentItem.foldername)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            holder.relativeTouch.context.startActivity(intent)
        }
    }
}