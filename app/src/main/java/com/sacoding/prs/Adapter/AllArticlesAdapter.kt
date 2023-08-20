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
import com.sacoding.prs.data.models.ArticleId
import com.sacoding.prs.databinding.ItemArticleBinding
import java.math.BigDecimal
import java.text.DecimalFormat

class AllArticlesAdapter : RecyclerView.Adapter<AllArticlesAdapter.AllArticlesViewHolder>() {
    val dbRef= FirebaseDatabase.getInstance().getReference("categories")

    inner class AllArticlesViewHolder(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<ArticleId>(){
        override fun areItemsTheSame(oldItem: ArticleId, newItem: ArticleId): Boolean {
            return oldItem.hashCode()==newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: ArticleId, newItem: ArticleId): Boolean {
            return oldItem==newItem
        }
    }
    val differ= AsyncListDiffer(this,differCallBack)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllArticlesAdapter.AllArticlesViewHolder {
        return AllArticlesViewHolder(ItemArticleBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: AllArticlesAdapter.AllArticlesViewHolder, position: Int)  {
        val currItem=differ.currentList[position]
        holder.binding.apply {
            tvArticleId.text="Article ID : "+currItem.article_id.toString()
            tvProductName.text = "Product Name : "+currItem.prod_name.toString()
            tvProductCategory.text = "Category ID : "+currItem.product_category.toString()
            val db=dbRef.child(currItem.product_category)
            db.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val imgUrl=snapshot.child("imgurl").getValue(String::class.java)
                    Glide.with(root.context).load(imgUrl).into(ivImgArticle)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("Img",error.toString())
                }
            })

            root.setOnClickListener{
                onItemClickListener?.onItemClick(currItem)
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: OnItemClickListener? = null

    // Define the OnItemClickListener interface
    interface OnItemClickListener {
        fun onItemClick(article: ArticleId)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

}