package com.sacoding.prs.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.sacoding.prs.data.models.History
import com.sacoding.prs.databinding.HistoryItemViewBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    val dbRef=FirebaseDatabase.getInstance().getReference("categories")
    inner class HistoryViewHolder(val binding: HistoryItemViewBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<History>(){
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem.hashCode()==newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem==newItem
        }
    }
    val differ= AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(HistoryItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val currItem=differ.currentList[position]
        holder.binding.apply {
            tvArticleId.text="Article ID : "+currItem.article_id.toString()
            tvProdCode.text="Product Code : "+currItem.product_code.toString()
            tvProdName.text="Product Name : "+currItem.prod_name.toString()
            tvProdCateg.text="Category : "+currItem.product_category.toString()
            val db=dbRef.child(currItem.product_category)
            db.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val imgUrl=snapshot.child("imgurl").getValue(String::class.java)
                    Glide.with(root.context).load(imgUrl).into(ivHistoryImage)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("Img",error.toString())
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}