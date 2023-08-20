package com.sacoding.prs.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sacoding.prs.data.models.RecommendedItem
import com.sacoding.prs.databinding.ItemRecommendedArticleBinding


class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

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
            tvProductId.text = currItem.article_id.toString()
            tvPercentage.text = currItem.probability.toString()
            tvProductCategory.text = currItem.product_category
            tvProductName.text = currItem.prod_name
        }
    }
}