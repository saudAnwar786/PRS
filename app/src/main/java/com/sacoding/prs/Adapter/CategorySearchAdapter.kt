package com.sacoding.prs.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sacoding.prs.data.models.Category
import com.sacoding.prs.databinding.ItemCategoryBinding

class CategorySearchAdapter: RecyclerView.Adapter<CategorySearchAdapter.CategorySearchViewHolder>() {

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
            tvCategoryProbability.text=currItem.Probability.toString()
            tvProdCategory.text=currItem.Product_category.toString()
            tvProdCode.text=currItem.product_code.toString()
            tvProdName.text=currItem.product_name.toString()
        }
    }

    override fun getItemCount(): Int=differ.currentList.size
}