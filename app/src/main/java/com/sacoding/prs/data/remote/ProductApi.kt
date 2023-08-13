package com.sacoding.prs.data.remote

import com.sacoding.prs.data.models.Product
import com.sacoding.prs.data.models.RecommendedItems
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ProductApi {

    @POST("/Recommended_items")
    suspend fun sendUserId(@Body userId:String):List<Product>

}