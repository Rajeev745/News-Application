package com.example.newsapplication.di

import com.example.newsapplication.data.remote.api.NewsApi
import com.example.newsapplication.data.repository.NewsRepositoryImpl
import com.example.newsapplication.domain.repository.NewsRepository
import com.example.newsapplication.domain.use_case.news_search.NewsSearchUseCase
import com.example.newsapplication.domain.use_case.news_sources.NewsSourceUseCase
import com.example.newsapplication.domain.use_case.trending_news.TrendingNewsUseCase
import com.example.newsapplication.presentation.viewmodel.NewsSearchViewModel
import com.example.newsapplication.presentation.viewmodel.TrendingNewsViewModel
import com.example.newsapplication.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesNewsRepository(api: NewsApi): NewsRepository {
        return NewsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesTrendingNewsCase(newsRepository: NewsRepository): TrendingNewsUseCase {
        return TrendingNewsUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun providesNewsSourceUseCase(newsRepository: NewsRepository): NewsSourceUseCase {
        return NewsSourceUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun providesNewsSearchUseCase(newsRepository: NewsRepository): NewsSearchUseCase {
        return NewsSearchUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun provideTrendingNewsViewModel(
        trendingNewsUseCase: TrendingNewsUseCase,
        newsSourceUseCase: NewsSourceUseCase
    ): TrendingNewsViewModel {
        return TrendingNewsViewModel(trendingNewsUseCase, newsSourceUseCase)
    }

    @Provides
    @Singleton
    fun provideNewsSearchViewModel(
        newsSearchUseCase: NewsSearchUseCase
    ): NewsSearchViewModel {
        return NewsSearchViewModel(newsSearchUseCase)
    }
}