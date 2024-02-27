package com.example.newsapplication.domain.models.news.trending

data class TrendingNewsModel(
    var status: String? = null,
    var totalResults: Int? = null,
    var articles: List<ArticlesModel> = listOf()
)