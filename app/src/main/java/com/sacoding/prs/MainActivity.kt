package com.sacoding.prs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sacoding.prs.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController=navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

       navController.addOnDestinationChangedListener{_,des,_->
           when(des.id){

                R.id.fragmentHistory->{
                    supportActionBar?.title = "History"
                }
                R.id.fragment_User,R.id.fragment1->{
                    supportActionBar?.title = "Byte Shift"
                }
               R.id.fragmentAllArticles->{
                   supportActionBar?.title = "All Products"
               }
               R.id.fragmentSearchByCategories,R.id.recommendedProductsFragment->{
                   supportActionBar?.title = "Recommendations"
               }
               R.id.fragment2->{
                   supportActionBar?.title = "More"
               }
           }

       }

    }
}