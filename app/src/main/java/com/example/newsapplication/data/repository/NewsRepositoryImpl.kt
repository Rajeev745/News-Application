package com.example.newsapplication.data.repository

import androidx.paging.PagingSource
import com.example.newsapplication.data.remote.api.NewsApi
import com.example.newsapplication.data.remote.dto.sources.NewsSourceDto
import com.example.newsapplication.data.remote.dto.trending.TrendingNewsDto
import com.example.newsapplication.domain.models.news.sources.NewsSourcesModel
import com.example.newsapplication.domain.models.news.trending.ArticlesModel
import com.example.newsapplication.domain.paging.NewsSourcesPagingModel
import com.example.newsapplication.domain.paging.TrendingNewsPagingSource
import com.example.newsapplication.domain.repository.NewsRepository
import com.example.newsapplication.utils.Constants.API_KEY
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: NewsApi
) : NewsRepository {

    override suspend fun getTrendingNews(
        country: String,
        page: Int,
        category: String
    ): PagingSource<Int, ArticlesModel> {
        return TrendingNewsPagingSource(api, country, category)
    }

    override suspend fun getNewsSources(category: String): NewsSourceDto {
        return api.getNewsSources(API_KEY, category)
    }

    override suspend fun getNewsSearch(q: String, sortBy: String, page: Int): TrendingNewsDto {
        return api.getNewsSearchResponse(API_KEY, q, sortBy, page)
    }
}