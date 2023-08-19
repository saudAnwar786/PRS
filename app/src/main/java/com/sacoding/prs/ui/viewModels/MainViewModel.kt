package com.sacoding.prs.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sacoding.prs.data.models.Articles
import com.sacoding.prs.data.models.RecommendedItems
import com.sacoding.prs.others.Resource
import com.sacoding.prs.repositories.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) :ViewModel(){

    val uiState: MutableLiveData<Resource<RecommendedItems>> = MutableLiveData()
    val uiStateForArticles: MutableLiveData<Resource<Articles>> = MutableLiveData()

//  init {
//      sendUserId(20)
//  }
    fun sendUserId(userId:Int){
        viewModelScope.launch {
            val result = repository.getAllRecommendProducts(userId)
            result.collectLatest { res->
                when(res){
                    is Resource.Success->{
                        uiState.postValue(Resource.Success(res.data!!))
                    }
                    is Resource.Error -> {
                        uiState.postValue(Resource.Error(res.message!!))
                    }
                    is Resource.Loading -> {
                        uiState.postValue(Resource.Loading())
                    }
                    else ->{}
                }
            }
        }
    }
    fun getAllArticles(){
        viewModelScope.launch {
            val result=repository.getAllArticles()
            result.collectLatest {
                when(it){
                    is Resource.Success->{
                        uiStateForArticles.postValue(Resource.Success(it.data!!))
                    }

                    is Resource.Error -> uiStateForArticles.postValue(Resource.Error(it.message!!))

                    is Resource.Loading -> {
                        uiStateForArticles.postValue(Resource.Loading())
                    }

                    else -> {}
                }
            }
        }
    }
}