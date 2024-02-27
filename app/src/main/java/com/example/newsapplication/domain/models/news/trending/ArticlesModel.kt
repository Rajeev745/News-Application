package com.example.newsapplication.domain.models.news.trending

data class ArticlesModel(
    var source: SourceModel = SourceModel(),
    var author: String? = null,
    var title: String? = null,
    var description: String? = null,
    var url: String? = null,
    var urlToImage: String? = null,
    var publishedAt: String? = null,
    var content: String? = null
)