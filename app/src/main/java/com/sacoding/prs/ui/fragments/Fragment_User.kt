 package com.sacoding.prs.ui.fragments

 import android.app.Activity
 import android.app.AlertDialog
 import android.app.Dialog
 import android.content.Intent
 import android.os.Bundle
 import android.util.Log
 import android.view.LayoutInflater
 import android.view.View
 import android.view.ViewGroup
 import androidx.fragment.app.Fragment
 import androidx.fragment.app.FragmentManager
 import androidx.fragment.app.viewModels
 import androidx.lifecycle.Observer
 import androidx.navigation.NavArgs
 import androidx.navigation.fragment.findNavController
 import androidx.navigation.fragment.navArgs
 import androidx.recyclerview.widget.LinearLayoutManager
 import com.google.android.material.snackbar.Snackbar
 import com.google.gson.Gson
 import com.sacoding.prs.Adapter.MainAdapter
 import com.sacoding.prs.R
 import com.sacoding.prs.data.models.ArrayParcleable
 import com.sacoding.prs.databinding.Fragment1Binding
 import com.sacoding.prs.databinding.FragmentUserBinding
 import com.sacoding.prs.others.Constants
 import com.sacoding.prs.others.DataListener
 import com.sacoding.prs.others.MyDialog
 import com.sacoding.prs.others.Resource
 import com.sacoding.prs.ui.viewModels.MainViewModel
 import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class Fragment_User : Fragment(R.layout.fragment__user),DataListener {
    val REQ_CODE = 10
    private lateinit var binding: FragmentUserBinding
    private val args: Fragment_UserArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserBinding.bind(view)
        binding.tvUserId.text = "User ID: "+args.userId
        binding.llRecommendProducts.setOnClickListener {
            val bundle = Bundle().apply {
                putString("user_id", args.userId)
            }
            findNavController().navigate(
                R.id.action_fragment_User_to_recommendedProductsFragment,
                bundle
            )
        }
        binding.llAllProducts.setOnClickListener {
            val bundle = Bundle().apply {
                putString("user_id", args.userId)
            }
            findNavController().navigate(R.id.action_fragment_User_to_fragmentAllArticles, bundle)
        }
        binding.llUserHistory.setOnClickListener {
            val bundle = Bundle().apply {
                putString("user_id", args.userId)
            }
            findNavController().navigate(R.id.action_fragment_User_to_fragmentHistory, bundle)
        }
        binding.llCategory.setOnClickListener {
            val dialog = MyDialog()
            dialog.setTargetFragment(this, REQ_CODE)
            dialog.show(parentFragmentManager, "mydialog")
        }
    }

    override fun onDataReceived(data: ArrayList<String>) {
        goToCategFragment(data)
    }

    private fun goToCategFragment(data: ArrayList<String>) {
        val fList=ArrayParcleable(data)
        val bundle=Bundle().apply {
            putString("user_id",args.userId)
            putSerializable("list",fList)
        }
        findNavController().navigate(R.id.action_fragment_User_to_fragmentSearchByCategories,bundle)
    }
}
