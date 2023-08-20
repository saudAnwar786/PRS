package com.sacoding.prs.ui.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sacoding.prs.data.models.ArticleDetail
import com.sacoding.prs.data.models.Articles
import com.sacoding.prs.data.models.ProductCategory
import com.sacoding.prs.data.models.ProductRecommended
import com.sacoding.prs.data.models.UserHistory
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

    val uiState: MutableLiveData<Resource<ProductRecommended>> = MutableLiveData()
    val uiStateForArticles: MutableLiveData<Resource<Articles>> = MutableLiveData()

    val articleDetail:MutableLiveData<Resource<ArticleDetail>> = MutableLiveData()
    val uiStateUserHistory:MutableLiveData<Resource<UserHistory>> = MutableLiveData()

    val uiStateProductCategory:MutableLiveData<Resource<ProductCategory>> = MutableLiveData()
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
    fun getArticleDetail(userId:Int,articleId:Long){
        viewModelScope.launch {
            val result = repository.getArticleDetail(userId, articleId)
            result.collectLatest {
                when(it){
                    is Resource.Error -> {
                        articleDetail.postValue(Resource.Error(it.message?:"Unknown error occurred"))
                    }
                    is Resource.Loading -> {
                        articleDetail.postValue(Resource.Loading())
                    }
                    is Resource.Success -> {
                        articleDetail.postValue(Resource.Success(it.data!!))
                    }
                }
            }
        }
    }
    fun getUserHistory(userId: Int){
        viewModelScope.launch {
            val result = repository.getUserHistory(userId)
            result.collectLatest {
                when(it){
                    is Resource.Error -> {
                        uiStateUserHistory.postValue(Resource.Error(it.message?:"An Unknown error occurred"))
                    }
                    is Resource.Loading -> {
                        uiStateUserHistory.postValue(Resource.Loading())
                    }
                    is Resource.Success -> {
                        uiStateUserHistory.postValue(Resource.Success(it.data!!))
                    }
                }
            }
        }
    }
    fun getProductCategory(userId: Int,category:String){
        viewModelScope.launch {
            val result = repository.getProductCategory(userId, category)
            result.collectLatest {
                when(it){
                    is Resource.Error -> {
                        uiStateProductCategory.postValue(Resource.Error(it.message?:"unknown error occurred"))
                    }
                    is Resource.Loading -> {
                        uiStateProductCategory.postValue(Resource.Loading())
                    }
                    is Resource.Success -> {
                        uiStateProductCategory.postValue(Resource.Success(it.data!!))
                    }
                }
            }
        }
    }
}