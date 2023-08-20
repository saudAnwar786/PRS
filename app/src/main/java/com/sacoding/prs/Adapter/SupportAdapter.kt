package com.sacoding.prs.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sacoding.prs.data.models.SupportItem
import com.sacoding.prs.databinding.ItemSupportBinding

class SupportAdapter(private val items : List<SupportItem>) :
    RecyclerView.Adapter<SupportAdapter.ItemsViewHolder>() {

    inner class ItemsViewHolder(val binding : ItemSupportBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        return ItemsViewHolder(ItemSupportBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val item=items[position]
        holder.binding.apply {
            ivImageSupport.setImageResource(item.img)
            tvTopicSupport.text=item.title
            tvSupportContent.text=item.content
        }

    }
}