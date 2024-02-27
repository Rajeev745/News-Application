package com.example.newsapplication.data.remote.dto.trending

import com.example.newsapplication.domain.models.news.trending.TrendingNewsModel
import com.google.gson.annotations.SerializedName

data class TrendingNewsDto(
    @SerializedName("status") var status: String? = null,
    @SerializedName("totalResults") var totalResults: Int? = null,
    @SerializedName("articles") var articles: List<ArticlesDto> = listOf()
)

fun TrendingNewsDto.toTrendingNewsModel(): TrendingNewsModel {
    return TrendingNewsModel(
        status = status,
        totalResults = totalResults,
        articles = articles.map { it.toArticlesModel() }
    )
}