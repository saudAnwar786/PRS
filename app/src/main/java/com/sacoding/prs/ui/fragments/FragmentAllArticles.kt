package com.sacoding.prs.ui.fragments

import android.os.Bundle
import android.view.View
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
            override fun onItemClick(article: Double) {

            }
        })
        viewModel.uiStateForArticles.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success->{
                    articlesAdapter.differ.submitList(it.data?.Articles_id)
                }
                is Resource.Error -> {
                    Snackbar.make(binding.root,"${it.message}", Snackbar.LENGTH_LONG).show()
                }
                is Resource.Loading -> {

                }

            }
        })
    }

    private fun setUpRecyclerView(){
        articlesAdapter= AllArticlesAdapter()
        binding.AllArticlesRecyclerView.apply {
            adapter=articlesAdapter
            layoutManager= LinearLayoutManager(activity)
        }
    }
}