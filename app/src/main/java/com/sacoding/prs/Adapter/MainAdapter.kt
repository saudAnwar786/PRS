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
import com.sacoding.prs.data.models.RecommendedItem
import com.sacoding.prs.databinding.ItemRecommendedArticleBinding


class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    val dbRef= FirebaseDatabase.getInstance().getReference("categories")

    inner class MainViewHolder(val binding: ItemRecommendedArticleBinding):RecyclerView.ViewHolder(binding.root)

    val differCallBack = object : DiffUtil.ItemCallback<RecommendedItem>(){
        override fun areItemsTheSame(
            oldItem: RecommendedItem,
            newItem: RecommendedItem
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem: RecommendedItem,
            newItem: RecommendedItem
        ): Boolean {
            return oldItem == newItem
        }

    }
    val differ  = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(ItemRecommendedArticleBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return  differ.currentList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val currItem = differ.currentList[position]
        holder.binding.apply {
            val formattedNumber = String.format("%.3f",currItem.probability*100)
            tvProductId.text = "Product ID : "+currItem.article_id.toString()
            tvPercentage.text = "Buying Probability : "+formattedNumber+"%"
            tvProductCategory.text = "Category : "+currItem.product_category
            tvProductName.text = "Product Name : "+currItem.prod_name
            val db=dbRef.child(currItem.product_category)
            db.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val imgUrl=snapshot.child("imgurl").getValue(String::class.java)
                    Glide.with(root.context).load(imgUrl).into(ivImgRecommend)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("Img",error.toString())
                }
            })
        }
    }
}