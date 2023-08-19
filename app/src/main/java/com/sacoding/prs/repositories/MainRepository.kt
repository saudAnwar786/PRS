package com.sacoding.prs.repositories

import android.util.Log
import com.sacoding.prs.data.remote.ProductApi
import com.sacoding.prs.others.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
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
        emit( Resource.Error("server not reachable"))
        return@flow
        }
        emit(Resource.Success(response))
    }
}