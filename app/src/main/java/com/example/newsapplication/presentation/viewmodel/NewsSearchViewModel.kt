package com.example.newsapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.common.Resource
import com.example.newsapplication.domain.models.news.trending.TrendingNewsModel
import com.example.newsapplication.domain.use_case.news_search.NewsSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsSearchViewModel @Inject constructor(
    private val newsSearchUseCase: NewsSearchUseCase
) : ViewModel() {

    private val _newsSearchResponse =
        MutableStateFlow<Resource<TrendingNewsModel>>(Resource.Unspecified())
    val newsSearchResponse = _newsSearchResponse.asStateFlow()

    fun getNewsSearch(q: String, sortBy: String, page: Int) {
        viewModelScope.launch {
            newsSearchUseCase.invoke(q, sortBy, page).collect() {
                _newsSearchResponse.value = it
            }
        }
    }
}