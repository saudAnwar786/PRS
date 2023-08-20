package com.sacoding.prs.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sacoding.prs.data.models.ArticleId
import com.sacoding.prs.databinding.ItemArticleBinding
import java.math.BigDecimal
import java.text.DecimalFormat

class AllArticlesAdapter : RecyclerView.Adapter<AllArticlesAdapter.AllArticlesViewHolder>() {

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
//        Log.d("Tag", "${currItem}")
        holder.binding.apply {
//            val fetchedValue = currItem // For example, fetched value is 10^8 in scientific notation
//            val decimalFormat = DecimalFormat("#########") // Define the format you want
//            val formattedValue = decimalFormat.format(fetchedValue)
            tvArticleId.text=currItem.article_id.toString()
            tvProductName.text = currItem.prod_name.toString()
            tvProductCategory.text = currItem.product_category.toString()

            root.setOnClickListener{
//                val value = BigDecimal(formattedValue.toString());
//                val doubleValue = value.toDouble();
//                Log.d("Tag", "${doubleValue}")
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