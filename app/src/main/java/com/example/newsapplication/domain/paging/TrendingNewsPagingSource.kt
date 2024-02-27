package com.example.newsapplication.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapplication.data.remote.api.NewsApi
import com.example.newsapplication.data.remote.dto.trending.toTrendingNewsModel
import com.example.newsapplication.domain.models.news.trending.ArticlesModel
import com.example.newsapplication.domain.repository.NewsRepository
import com.example.newsapplication.utils.Constants
import retrofit2.HttpException
import java.io.IOException

class TrendingNewsPagingSource(
    private val api: NewsApi,
    private val country: String,
    private val category: String
) : PagingSource<Int, ArticlesModel>() {

    override fun getRefreshKey(state: PagingState<Int, ArticlesModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesModel> {
        val page = params.key ?: 1
        return try {
            val articles =
                api.getTrendingNews(Constants.API_KEY, country, page, category).toTrendingNewsModel().articles

            val nextKey = if (articles.isEmpty()) {
                null
            } else {
                page + 1
            }

            LoadResult.Page(
                data = articles,
                prevKey = if (page == 1) null else page,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}