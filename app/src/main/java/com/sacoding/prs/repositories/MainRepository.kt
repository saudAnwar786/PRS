package com.sacoding.prs.repositories

import com.sacoding.prs.data.models.RecommendedItems
import com.sacoding.prs.data.remote.ProductApi
import com.sacoding.prs.others.Resource
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.http.Body
import java.io.IOException
import javax.inject.Inject


class MainRepository @Inject constructor(
    private val api:ProductApi
) {

    suspend fun sendUserId( userId:String) = flow{
        emit(Resource.Loading())
        val response = try {
            api.sendUserId(userId)
        }catch (e: IOException){
             emit(Resource.Error("IO Exception"))
            return@flow
        }catch (e: HttpException){
            emit( Resource.Error("server not reachable"))
            return@flow
        }
        emit(Resource.Success(response))

    }
}