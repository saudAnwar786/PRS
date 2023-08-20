package com.sacoding.prs.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.sacoding.prs.Adapter.AllArticlesAdapter
import com.sacoding.prs.Adapter.HistoryAdapter
import com.sacoding.prs.R
import com.sacoding.prs.databinding.FragmentAllArticlesBinding
import com.sacoding.prs.databinding.FragmentHistoryBinding
import com.sacoding.prs.others.Resource
import com.sacoding.prs.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentHistory: Fragment(R.layout.fragment_history) {
    private lateinit var binding: FragmentHistoryBinding
    private val args : FragmentHistoryArgs by navArgs()
    private val viewModel : MainViewModel by viewModels()
    private lateinit var  historyAdapter : HistoryAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentHistoryBinding.bind(view)
        setUpRecyclerView()
        viewModel.getUserHistory(args.userId.toInt())
        viewModel.uiStateUserHistory.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success->{
                    binding.progressBar.visibility=View.GONE
                    historyAdapter.differ.submitList(it.data?.History)
                }

                is Resource.Error -> binding.progressBar.visibility=View.GONE
                is Resource.Loading -> binding.progressBar.visibility=View.VISIBLE
            }
        })
    }

    private fun setUpRecyclerView(){
        historyAdapter= HistoryAdapter()
        binding.UserHistoryRecyclerView.apply {
            adapter=historyAdapter
            layoutManager=LinearLayoutManager(activity)
        }
    }
}