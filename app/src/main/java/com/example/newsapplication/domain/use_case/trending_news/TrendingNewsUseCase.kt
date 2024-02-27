package com.example.newsapplication.domain.use_case.trending_news

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapplication.common.Resource
import com.example.newsapplication.domain.models.news.trending.ArticlesModel
import com.example.newsapplication.domain.paging.TrendingNewsPagingSource
import com.example.newsapplication.domain.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TrendingNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) {

    operator fun invoke(
        country: String, category: String
    ): Flow<PagingData<ArticlesModel>> = flow {
        val pagingSource = repository.getTrendingNews(country, 1, category)
        Pager(config = PagingConfig(pageSize = 20)) {
            pagingSource
        }.flow.collect { pagingData ->
            emit(pagingData)
        }
    }.flowOn(Dispatchers.IO)
}