package com.sacoding.prs.data.models

data class RecommendedItem(
    val article_id: Int,
    val probability: Double,
    val prod_name: String,
    val product_category: String
)