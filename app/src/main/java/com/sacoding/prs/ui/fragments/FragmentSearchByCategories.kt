package com.sacoding.prs.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.sacoding.prs.R
import com.sacoding.prs.databinding.FragmentSearchByCategoriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSearchByCategories: Fragment(R.layout.fragment_search_by_categories) {
    private lateinit var binding: FragmentSearchByCategoriesBinding
    private val args: FragmentSearchByCategoriesArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentSearchByCategoriesBinding.bind(view)
        val userId=args.userId.toInt()
//        val list=args.list.toList()
//        Log.d("Tag",list.toString())


    }
}