package com.example.newsapplication.domain.models.filter

import com.example.newsapplication.utils.FilterConstants

data class FilterModel(
    var category: String, var isSelected: Boolean, var filterCategory: String
)

fun getCategoryList(): List<FilterModel> {
    return listOf(
        FilterModel("All", true, FilterConstants.ALL),
        FilterModel("business", false, FilterConstants.BUSINESS),
        FilterModel("entertainment", false, FilterConstants.ENTERTAINMENT),
        FilterModel("general", false, FilterConstants.GENERAL),
        FilterModel("health", false, FilterConstants.HEALTH),
        FilterModel("science", false, FilterConstants.SCIENCE),
        FilterModel("sports", false, FilterConstants.SPORTS),
        FilterModel("technology", false, FilterConstants.TECHNOLOGY)
    )
}
