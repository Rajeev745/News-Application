package com.example.newsapplication.data.remote.dto.sources

import com.example.newsapplication.domain.models.news.sources.NewsSourcesModel
import com.google.gson.annotations.SerializedName

data class NewsSourcesDto(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("category") var category: String? = null,
    @SerializedName("language") var language: String? = null,
    @SerializedName("country") var country: String? = null
)

fun NewsSourcesDto.toNewsSourcesModel(): NewsSourcesModel {
    return NewsSourcesModel(
        id = id,
        name = name,
        description = description,
        url = url,
        category = category,
        language = language,
        country = country
    )
}