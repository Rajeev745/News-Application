package com.example.newsapplication.data.remote.dto.trending

import com.example.newsapplication.domain.models.news.trending.ArticlesModel
import com.google.gson.annotations.SerializedName

data class ArticlesDto(
    @SerializedName("source") var source: SourceDto = SourceDto(),
    @SerializedName("author") var author: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("urlToImage") var urlToImage: String? = null,
    @SerializedName("publishedAt") var publishedAt: String? = null,
    @SerializedName("content") var content: String? = null
)

fun ArticlesDto.toArticlesModel(): ArticlesModel {
    return ArticlesModel(
        source = source.toSourceModel(),
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}