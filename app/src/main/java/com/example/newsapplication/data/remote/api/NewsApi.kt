package com.example.newsapplication.data.remote.api

import com.example.newsapplication.data.remote.dto.sources.NewsSourceDto
import com.example.newsapplication.data.remote.dto.trending.TrendingNewsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getTrendingNews(
        @Query("apiKey") apiKey: String,
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("category") category: String
    ): TrendingNewsDto

    @GET("everything")
    suspend fun getNewsSearchResponse(
        @Query("apiKey") apiKey: String,
        @Query("q") q: String,
        @Query("sortBy") sortBy: String,
        @Query("page") page: Int,
    ): TrendingNewsDto

    @GET("top-headlines/sources")
    suspend fun getNewsSources(
        @Query("apiKey") apiKey: String,
        @Query("category") category: String
    ): NewsSourceDto
}