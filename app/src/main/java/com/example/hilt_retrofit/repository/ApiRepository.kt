package com.example.hilt_retrofit.repository

import com.example.hilt_retrofit.api.ApiService
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


class ApiRepository @Inject constructor(private val apiServices: ApiService) {
    suspend fun getPopularMoviesList(page: Int) = apiServices.getPopularMoviesList(page)

    suspend fun getMovieDetails(id: Int) =
        apiServices.getMovieDetails(id)
}