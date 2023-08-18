package com.sacoding.prs.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sacoding.prs.Adapter.MainAdapter
import com.sacoding.prs.databinding.Fragment1Binding
import com.sacoding.prs.others.Resource
import com.sacoding.prs.ui.viewModels.MainViewModel

class RecommendedProductsFragment:Fragment() {
    private lateinit var binding: Fragment1Binding
    private lateinit var mainAdapter: MainAdapter
    private val viewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = Fragment1Binding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        viewModel.uiState.observe(viewLifecycleOwner, Observer{ res->
            when(res){
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(binding.root,"${res.message}", Snackbar.LENGTH_LONG).show()
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE

                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    mainAdapter.differ.submitList(res.data)
                }
            }
        })



    }
    private fun setUpRecyclerView(){

    }
}