package com.sacoding.prs.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sacoding.prs.data.models.History
import com.sacoding.prs.databinding.HistoryItemViewBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
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
            tvArticleId.text=currItem.article_id.toString()
            tvProdCode.text=currItem.product_code.toString()
            tvProdName.text=currItem.prod_name.toString()
            tvProdCateg.text=currItem.product_category.toString()
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}