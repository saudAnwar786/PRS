package com.sacoding.prs.repositories

import android.util.Log
import com.sacoding.prs.data.models.ArticleDetail
import com.sacoding.prs.data.remote.ProductApi
import com.sacoding.prs.others.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import retrofit2.HttpException
import retrofit2.http.Query
import java.io.IOException
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val api:ProductApi
) {

    suspend fun getAllRecommendProducts(userId:Int) = flow{
        emit(Resource.Loading())
        val response = try {
            api.getAllRecommended(userId)
        }catch (e: IOException){
             emit(Resource.Error(e.message?:""))
                Log.d("Tag",e.message.toString())
            return@flow
        }catch (e: HttpException){
            emit( Resource.Error("server not reachable"))
            return@flow
        }
        emit(Resource.Success(response))

    }

    suspend fun getAllArticles()= flow{
        emit(Resource.Loading())
        val response=try {
            api.getAllArticles()
        }catch (e:IOException){
            emit(Resource.Error(e.message?:""))
            Log.d("Tag",e.message.toString())
            return@flow
        }catch (e: HttpException){
        emit( Resource.Error(e.message()))
            Log.d("Tag",e.message.toString())
        return@flow
        }
        emit(Resource.Success(response))
    }

    suspend fun getArticleDetail( userId:Int, articleId:Long) = flow {
        emit(Resource.Loading())
        val response = try{
            api.getArticleDetail(userId,articleId)
        }catch (e:IOException){
            emit(Resource.Error(e.message?:""))
            Log.d("Tag",e.message.toString())
            return@flow
        }catch (e: HttpException){
            emit( Resource.Error("server not reachable"))
            return@flow
        }
        emit(Resource.Success(response))

    }
    suspend fun getUserHistory(userId:Int) = flow{
        emit(Resource.Loading())
        val response = try{
            api.getUserHistory(userId)
        }catch (e:IOException){
            emit(Resource.Error(e.message?:""))
            Log.d("Tag",e.message.toString())
            return@flow
        }catch (e: HttpException){
            emit( Resource.Error("server not reachable"))
            return@flow
        }
        emit(Resource.Success(response))
    }
    suspend fun getProductCategory(userId: Int,category:String) = flow {
        emit(Resource.Loading())
        val response = try{
            api.getProductCategory(userId, category)
        }catch (e:IOException){
            emit(Resource.Error(e.message?:""))
            Log.d("Tag",e.message.toString())
            return@flow
        }catch (e: HttpException){
            emit( Resource.Error("server not reachable"))
            return@flow
        }
        emit(Resource.Success(response))
    }
}