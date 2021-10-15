package com.danieloliveira.gistsandroid.data.data_remote.service

import com.danieloliveira.gistsandroid.data.data_remote.response.GistResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GistService {

    @GET("gists/public")
    suspend fun getGists(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<GistResponse>
}