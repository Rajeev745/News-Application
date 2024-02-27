package com.example.newsapplication.domain.use_case.news_search

import com.example.newsapplication.common.Resource
import com.example.newsapplication.data.remote.dto.trending.toTrendingNewsModel
import com.example.newsapplication.domain.models.news.trending.TrendingNewsModel
import com.example.newsapplication.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class NewsSearchUseCase(private val newsRepository: NewsRepository) {

    operator fun invoke(q: String, sortBy: String, page: Int): Flow<Resource<TrendingNewsModel>> =
        flow {
            try {
                emit(Resource.Loading())
                val trendingNewsModel = withContext(Dispatchers.IO) {
                    newsRepository.getNewsSearch(q, sortBy, page).toTrendingNewsModel()
                }
                emit(Resource.Success(trendingNewsModel))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unknown error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error(e.localizedMessage ?: "Please check your internet connection"))
            }
        }
}