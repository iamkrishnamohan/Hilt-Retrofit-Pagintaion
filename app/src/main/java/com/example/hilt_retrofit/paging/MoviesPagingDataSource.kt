package com.example.hilt_retrofit.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.hilt_retrofit.repository.ApiRepository
import com.ezatpanah.hilt_retrofit_youtube.response.MoviesListResponse
import retrofit2.HttpException

class MoviesPagingDataSource(private val repository: ApiRepository) :
    PagingSource<Int, MoviesListResponse.Result>() {

    override fun getRefreshKey(state: PagingState<Int, MoviesListResponse.Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesListResponse.Result> {
        return try {
            val currentPage = params.key ?: 1
            val response = repository.getPopularMoviesList(currentPage)
            val data = response.body()!!.results
            val responseData = mutableListOf<MoviesListResponse.Result>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}