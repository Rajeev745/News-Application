package com.example.newsapplication.domain.use_case.news_sources

import com.example.newsapplication.common.Resource
import com.example.newsapplication.data.remote.dto.sources.toNewsSourceModel
import com.example.newsapplication.domain.models.news.sources.NewsSourceModel
import com.example.newsapplication.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class NewsSourceUseCase(private val repository: NewsRepository) {

    operator fun invoke(category: String): Flow<Resource<NewsSourceModel>> = flow {
        try {
            emit(Resource.Loading())
            val newsSourceModel = withContext(Dispatchers.IO) {
                repository.getNewsSources(category).toNewsSourceModel()
            }
            emit(Resource.Success(newsSourceModel))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Please check your Internet connection"))
        }
    }
}