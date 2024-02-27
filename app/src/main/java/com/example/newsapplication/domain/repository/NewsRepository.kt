package com.example.newsapplication.domain.repository

import androidx.paging.PagingSource
import com.example.newsapplication.data.remote.dto.sources.NewsSourceDto
import com.example.newsapplication.data.remote.dto.trending.TrendingNewsDto
import com.example.newsapplication.domain.models.news.sources.NewsSourcesModel
import com.example.newsapplication.domain.models.news.trending.ArticlesModel

interface NewsRepository {

    suspend fun getTrendingNews(country: String, page: Int, category: String): PagingSource<Int, ArticlesModel>

    suspend fun getNewsSources(category: String): NewsSourceDto

    suspend fun getNewsSearch(q: String, sortBy: String, page: Int): TrendingNewsDto
}