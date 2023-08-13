package com.sacoding.prs.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sacoding.prs.R
import com.sacoding.prs.databinding.Fragment2Binding

class Fragment2 :Fragment(R.layout.fragment2){
    private lateinit var binding: Fragment2Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = Fragment2Binding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }
}