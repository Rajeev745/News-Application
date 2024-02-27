package com.example.newsapplication.data.remote.dto.sources

import com.example.newsapplication.domain.models.news.sources.NewsSourceModel
import com.google.gson.annotations.SerializedName

data class NewsSourceDto(
    @SerializedName("status") var status: String? = null,
    @SerializedName("sources") var sources: List<NewsSourcesDto> = listOf()
)

fun NewsSourceDto.toNewsSourceModel(): NewsSourceModel {
    return NewsSourceModel(
        status = status,
        sources = sources.map { it.toNewsSourcesModel() }
    )
}
