package com.example.newsapplication.domain.models.news.sources

data class NewsSourceModel(
    var status: String? = null,
    var sources: List<NewsSourcesModel> = listOf()
)