package com.example.newsapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsapplication.common.Resource
import com.example.newsapplication.domain.models.news.sources.NewsSourceModel
import com.example.newsapplication.domain.models.news.sources.NewsSourcesModel
import com.example.newsapplication.domain.models.news.trending.ArticlesModel
import com.example.newsapplication.domain.use_case.news_sources.NewsSourceUseCase
import com.example.newsapplication.domain.use_case.trending_news.TrendingNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingNewsViewModel @Inject constructor(
    private val trendingNewsUseCase: TrendingNewsUseCase,
    private val newsSourceUseCase: NewsSourceUseCase
) : ViewModel() {

    private val _newsSources = MutableStateFlow<Resource<NewsSourceModel>>(Resource.Unspecified())
    val newsSources = _newsSources.asStateFlow()

    fun fetchTrendingNews(country: String, category: String): Flow<PagingData<ArticlesModel>> {
        return trendingNewsUseCase.invoke(country, category).cachedIn(viewModelScope)
    }

    fun fetchNewsSource(category: String) {
        viewModelScope.launch {
            newsSourceUseCase.invoke(category).collect() {
                _newsSources.value = it
            }
        }
    }
}