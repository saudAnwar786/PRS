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
import com.sacoding.prs.data.models.Category
import com.sacoding.prs.databinding.ItemCategoryBinding

class CategorySearchAdapter: RecyclerView.Adapter<CategorySearchAdapter.CategorySearchViewHolder>() {
    val dbRef= FirebaseDatabase.getInstance().getReference("categories")

    inner class CategorySearchViewHolder(val binding : ItemCategoryBinding):RecyclerView.ViewHolder(binding.root)

    val differCallBack = object : DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.hashCode()==newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem==newItem
        }
    }
    val differ=AsyncListDiffer(this,differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorySearchViewHolder {
        return CategorySearchViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CategorySearchViewHolder, position: Int) {
        val currItem=differ.currentList[position]
        holder.binding.apply {
            val formattedNumber = String.format("%.3f",currItem.Probability*100)
            tvCategoryProbability.text=formattedNumber+"%"
            tvProdCategory.text=currItem.Product_category.toString()
            tvProdName.text=currItem.product_name.toString()
            tvArticleId.text=currItem.article_id.toString()
            val db=dbRef.child(currItem.Product_category)
            db.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val imgUrl=snapshot.child("imgurl").getValue(String::class.java)
                    Glide.with(root.context).load(imgUrl).into(ivImgCategory)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("Img",error.toString())
                }
            })
        }
    }

    override fun getItemCount(): Int=differ.currentList.size
}