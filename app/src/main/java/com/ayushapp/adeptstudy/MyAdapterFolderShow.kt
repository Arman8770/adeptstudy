package com.ayushapp.adeptstudy

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class MyAdapterFolderShow(private val folderShow: ArrayList<FolderInfoModel>, grad: String):
    RecyclerView.Adapter<MyAdapterFolderShow.MyAdapterFolderShowView>() {

    class MyAdapterFolderShowView(itemView: View) : RecyclerView.ViewHolder(itemView){


        var folderNameshow:TextView = itemView.findViewById(R.id.folderName)
        var rlFolderTouch:RelativeLayout = itemView.findViewById(R.id.onFolderTouch)
        var imgdeleteTouch:ImageView = itemView.findViewById(R.id.deleteFolder)



    }

    private var dataDeleteRef= FirebaseDatabase.getInstance()
    private var storageDelRef = FirebaseStorage.getInstance()
    private var gradDelete:String = grad
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterFolderShowView {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.single_row_design,parent,false)
        return MyAdapterFolderShowView(view)
    }


    override fun getItemCount(): Int {
        return folderShow.size
    }


    override fun onBindViewHolder(holder: MyAdapterFolderShowView, position: Int) {
        val currentItem = folderShow[position]

        holder.folderNameshow.setText(currentItem.foldername)
        holder.rlFolderTouch.setOnClickListener{
            val intent = Intent(holder.rlFolderTouch.context,ViewFolder::class.java)
            intent.putExtra("foldername",currentItem.foldername)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            holder.rlFolderTouch.context.startActivity(intent)
        }

        if (personEmail=="ayush.adeptstudy@gmail.com"){
            holder.imgdeleteTouch.visibility = View.VISIBLE
        }else {
            holder.imgdeleteTouch.visibility = View.INVISIBLE
        }

        holder.imgdeleteTouch.setOnClickListener{

            val data=dataDeleteRef.getReference(""+gradDelete)
                val child = data.orderByChild("foldername")
                    .equalTo(""+currentItem.foldername)
                child.addValueEventListener(object :ValueEventListener{
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
            val deleteRef = childStorageRef.child(currentItem.foldername+"/"+"file.txt")
            deleteRef.delete()
            folderShow.removeAt(position)
            notifyDataSetChanged()
        }
    }
}