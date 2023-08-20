package com.sacoding.prs.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sacoding.prs.Adapter.AllArticlesAdapter
import com.sacoding.prs.Adapter.MainAdapter
import com.sacoding.prs.R
import com.sacoding.prs.data.models.ArticleId
import com.sacoding.prs.databinding.FragmentAllArticlesBinding
import com.sacoding.prs.others.Resource
import com.sacoding.prs.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAllArticles : Fragment(R.layout.fragment_all_articles) {
    private lateinit var binding: FragmentAllArticlesBinding
    private val args : FragmentAllArticlesArgs by navArgs()
    private val viewModel : MainViewModel by viewModels()
    private lateinit var articlesAdapter : AllArticlesAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentAllArticlesBinding.bind(view)

        setUpRecyclerView()
        viewModel.getAllArticles()
        articlesAdapter.setOnItemClickListener(object :AllArticlesAdapter.OnItemClickListener{
            override fun onItemClick(article: ArticleId) {
                viewModel.getArticleDetail(args.userId.toInt(),article.article_id)
                viewModel.articleDetail.observe(viewLifecycleOwner, Observer {
                    when(it){
                        is Resource.Success->{
                            val prodName=it.data?.prod_name ?: ""
                            val prodProbab=it.data?.probability.toString() ?: ""
                            val prodId=it.data?.article_id.toString()?:""
                            showDialog(prodId,prodName,prodProbab)
                        }

                        is Resource.Error -> Toast.makeText(requireContext(),"${it.message}",Toast.LENGTH_LONG).show()
                        is Resource.Loading -> {
                            Snackbar.make(view,"Loading...",Snackbar.LENGTH_LONG).show()
                        }
                    }
                })
            }
        })
        viewModel.uiStateForArticles.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success->{
                    articlesAdapter.differ.submitList(it.data?.Article_id)
                }
                is Resource.Error -> {
                    Snackbar.make(binding.root,"${it.message}", Snackbar.LENGTH_LONG).show()
                }
                is Resource.Loading -> {

                }

            }
        })
    }


    private fun showDialog(prodId: String, prodName: String, prodProbab: String) {
        val dialog=Dialog(requireContext()).apply {
            setContentView(R.layout.article_dialog)
            setCancelable(true)
        }
        val tv_prodName=dialog.findViewById<TextView>(R.id.tv_product_name)
        val tv_prodId=dialog.findViewById<TextView>(R.id.tv_product_id)
        val tv_prodProbab=dialog.findViewById<TextView>(R.id.tv_product_buying_probability)
        tv_prodName.text=prodName
        tv_prodId.text=prodId
        tv_prodProbab.text=prodProbab
        dialog.show()
    }

    private fun setUpRecyclerView(){
        articlesAdapter= AllArticlesAdapter()
        binding.AllArticlesRecyclerView.apply {
            adapter=articlesAdapter
            layoutManager= LinearLayoutManager(activity)
        }
    }
}