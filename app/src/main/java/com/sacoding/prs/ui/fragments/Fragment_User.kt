 package com.sacoding.prs.ui.fragments

 import android.os.Bundle
 import android.view.LayoutInflater
 import android.view.View
 import android.view.ViewGroup
 import androidx.fragment.app.Fragment
 import androidx.fragment.app.viewModels
 import androidx.lifecycle.Observer
 import androidx.navigation.NavArgs
 import androidx.navigation.fragment.navArgs
 import androidx.recyclerview.widget.LinearLayoutManager
 import com.google.android.material.snackbar.Snackbar
 import com.sacoding.prs.Adapter.MainAdapter
 import com.sacoding.prs.R
 import com.sacoding.prs.databinding.Fragment1Binding
 import com.sacoding.prs.databinding.FragmentUserBinding
 import com.sacoding.prs.others.Resource
 import com.sacoding.prs.ui.viewModels.MainViewModel
 import dagger.hilt.android.AndroidEntryPoint

class Fragment_User : Fragment(R.layout.fragment__user){

   private lateinit var binding: FragmentUserBinding
    private val args : Fragment_UserArgs by navArgs()
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding= FragmentUserBinding.bind(view)
//      binding.tvUserId.text= getArguments()?.getString("user_id")
        binding.tvUserId.text=args.userId
  }

}