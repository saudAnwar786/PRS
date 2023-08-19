package com.sacoding.prs.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sacoding.prs.data.models.Articles
import com.sacoding.prs.databinding.ArticleItemBinding

class AllArticlesAdapter : RecyclerView.Adapter<AllArticlesAdapter.AllArticlesViewHolder>() {

    inner class AllArticlesViewHolder(val binding: ArticleItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Double>(){
        override fun areItemsTheSame(oldItem: Double, newItem: Double): Boolean {
            return oldItem.hashCode()==newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Double, newItem: Double): Boolean {
            return oldItem==newItem
        }
    }
    val differ= AsyncListDiffer(this,differCallBack)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllArticlesAdapter.AllArticlesViewHolder {
        return AllArticlesViewHolder(ArticleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: AllArticlesAdapter.AllArticlesViewHolder, position: Int) {
        val currItem=differ.currentList[position]
        holder.binding.apply {
            tvArticleId.text=currItem.toString()
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}