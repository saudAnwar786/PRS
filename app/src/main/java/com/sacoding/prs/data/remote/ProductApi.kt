package com.sacoding.prs.data.remote

import com.sacoding.prs.data.models.Product
import com.sacoding.prs.data.models.RecommendedItems
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ProductApi {

    @GET("/Recommended_items")
    suspend fun getAllRecommended(@Body userId:String):List<Product>

}