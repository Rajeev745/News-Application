package com.example.newsapplication.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsapplication.data.remote.api.NewsApi
import com.example.newsapplication.data.remote.dto.sources.toNewsSourceModel
import com.example.newsapplication.domain.models.news.sources.NewsSourcesModel
import com.example.newsapplication.domain.repository.NewsRepository
import com.example.newsapplication.utils.Constants
import retrofit2.HttpException
import java.io.IOException

class NewsSourcesPagingModel(
    private val api: NewsApi,
    private val category: String
) : PagingSource<Int, NewsSourcesModel>() {

    override fun getRefreshKey(state: PagingState<Int, NewsSourcesModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsSourcesModel> {
        val page = params.key ?: 1
        return try {
            val newsSource = api.getNewsSources(Constants.API_KEY, category).toNewsSourceModel().sources


            val nextKey = if (newsSource.isEmpty()) {
                null
            } else {
                page + 1
            }

            LoadResult.Page(
                data = newsSource,
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