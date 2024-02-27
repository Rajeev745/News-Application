package com.example.newsapplication.data.remote.dto.trending

import com.example.newsapplication.domain.models.news.trending.SourceModel
import com.google.gson.annotations.SerializedName

data class SourceDto(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null
)

fun SourceDto.toSourceModel(): SourceModel {
    return SourceModel(
        id = id,
        name = name
    )
}