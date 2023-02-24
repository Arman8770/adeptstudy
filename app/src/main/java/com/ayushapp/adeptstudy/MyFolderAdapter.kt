package com.ayushapp.adeptstudy

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class MyFolderAdapter(options: FirebaseRecyclerOptions<FileInfoModel>, folderName: String) :
    FirebaseRecyclerAdapter<FileInfoModel, MyFolderAdapter.MyFolderViewHolder>(options) {

    class MyFolderViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var pdfName =  itemView.findViewById<TextView>(R.id.pdfName)
        var pdftouch = itemView.findViewById<RelativeLayout>(R.id.pdftouchUp)
        var imgDeleteFile = itemView.findViewById<ImageView>(R.id.deleteFile)
    }

    private var dataDelFileRef = FirebaseDatabase.getInstance()
    private var storageDelRef = FirebaseStorage.getInstance()
    private var folderDel = folderName
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFolderViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.single_row_design_pdf,parent,false)
        return MyFolderViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyFolderViewHolder, position: Int, model: FileInfoModel) {
        holder.pdfName.setText(model.filename)
        if (personEmail=="ayush.adeptstudy@gmail.com"){
            holder.imgDeleteFile.visibility = View.VISIBLE
        }else {
            holder.imgDeleteFile.visibility = View.INVISIBLE
        }

        holder.pdftouch.setOnClickListener(){
            val intent = Intent(holder.pdftouch.context,ViewPdf::class.java)
            intent.putExtra("filename",model.filename)
            intent.putExtra("fileurl",model.fileurl)

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            holder.pdftouch.context.startActivity(intent)

            if (personEmail=="ayush.adeptstudy@gmail.com"){
                holder.imgDeleteFile.visibility = View.VISIBLE
            }else {
                holder.imgDeleteFile.visibility = View.INVISIBLE
            }

            holder.imgDeleteFile.setOnClickListener{
                val data=dataDelFileRef.getReference(""+folderDel)
                val child = data.orderByChild("filename")
                    .equalTo(""+model.filename)
                    child.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach{
                            val change:String= it.key.toString()
                            data.child(change).removeValue()
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
                val childStorageRef = storageDelRef.getReference(grad+"/")
                val deleteRef = childStorageRef.child(folderDel+"/"+model.filename+".pdf")
                deleteRef.delete()
            }
        }
    }
}