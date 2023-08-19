package com.sacoding.prs.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sacoding.prs.data.models.Articles
import com.sacoding.prs.databinding.ArticleItemBinding
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.util.Locale

class AllArticlesAdapter : RecyclerView.Adapter<AllArticlesAdapter.AllArticlesViewHolder>() {

    inner class AllArticlesViewHolder(val binding: ArticleItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Long>(){
        override fun areItemsTheSame(oldItem: Long, newItem: Long): Boolean {
            return oldItem.hashCode()==newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Long, newItem: Long): Boolean {
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

    override fun onBindViewHolder(holder: AllArticlesAdapter.AllArticlesViewHolder, position: Int)  {
        val currItem=differ.currentList[position]
//        Log.d("Tag", "${currItem}")
        holder.binding.apply {
            val fetchedValue = currItem // For example, fetched value is 10^8 in scientific notation
            val decimalFormat = DecimalFormat("#########") // Define the format you want
            val formattedValue = decimalFormat.format(fetchedValue)
            tvArticleId.text=formattedValue.toString()
            root.setOnClickListener{
                val value = BigDecimal(formattedValue.toString());
                val doubleValue = value.toDouble();
                Log.d("Tag", "${doubleValue}")

                onItemClickListener?.onItemClick(formattedValue.toLong())
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: OnItemClickListener? = null

    // Define the OnItemClickListener interface
    interface OnItemClickListener {
        fun onItemClick(article: Long)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

}