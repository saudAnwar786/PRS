package com.sacoding.prs.data.remote

import com.sacoding.prs.data.models.ArticleDetail
import com.sacoding.prs.data.models.Articles
import com.sacoding.prs.data.models.ProductCategory
import com.sacoding.prs.data.models.RecommendedItems
import com.sacoding.prs.data.models.UserHistory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ProductApi {

    @GET("/Recommended_items")
    suspend fun getAllRecommended(@Query("user_id")user_id : Int):RecommendedItems

//    @GET("/history_records")
//    suspend fun getHistoryOfUser(@Query("user_id")user_id : Int):

    @GET("/article_id")
    suspend fun getAllArticles() : Articles

    @GET("/search_by_article_id")
    suspend fun getArticleDetail(@Query("user_id") userId:Int,@Query("article_id") articleId:Long) : ArticleDetail

    @GET("/history_records")
    suspend fun getUserHistory(@Query("user_id") userId:Int):UserHistory

    @GET("/categorywise_recommendation")
    suspend fun getProductCategory(@Query("user_id") userId:Int,@Query("category") category:List<String>): ProductCategory

}