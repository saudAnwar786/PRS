package com.sacoding.prs.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sacoding.prs.R
import com.sacoding.prs.databinding.Fragment1Binding

class Fragment1:Fragment(R.layout.fragment1) {
    private lateinit var binding: Fragment1Binding
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
    }
}