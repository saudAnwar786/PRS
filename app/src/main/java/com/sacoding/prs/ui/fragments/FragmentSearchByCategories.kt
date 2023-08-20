package com.sacoding.prs.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.sacoding.prs.Adapter.CategorySearchAdapter
import com.sacoding.prs.Adapter.HistoryAdapter
import com.sacoding.prs.R
import com.sacoding.prs.data.models.ArrayParcleable
import com.sacoding.prs.databinding.FragmentSearchByCategoriesBinding
import com.sacoding.prs.others.Resource
import com.sacoding.prs.ui.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class FragmentSearchByCategories: Fragment(R.layout.fragment_search_by_categories) {
    private lateinit var binding: FragmentSearchByCategoriesBinding
    private val args: FragmentSearchByCategoriesArgs by navArgs()
    private val viewModel : MainViewModel by viewModels()
    private lateinit var categoryAdapter : CategorySearchAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding= FragmentSearchByCategoriesBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        val userId=args.userId.toInt()

        val list=args.list.data
        setUpRecyclerView()
        val jsonArray = JSONArray(list)
        val categoryQueryParam = jsonArray.toString()
        viewModel.getProductCategory(userId,categoryQueryParam)
        viewModel.uiStateProductCategory.observe(viewLifecycleOwner, Observer {
            when(it){
                is Resource.Success->{
                    binding.progressBar.visibility=View.GONE
                    categoryAdapter.differ.submitList(it.data?.Category)
                }

                is Resource.Error -> {
                    Snackbar.make(view, "${ it.message }",Snackbar.LENGTH_LONG).show()
                    binding.progressBar.visibility=View.GONE

                }
                is Resource.Loading -> {
                    binding.progressBar.visibility=View.VISIBLE

                }
            }
        })


    }
    private fun setUpRecyclerView(){
        categoryAdapter= CategorySearchAdapter()
        binding.categoryRv.apply {
            adapter=categoryAdapter
            layoutManager= LinearLayoutManager(activity)
        }
    }
}