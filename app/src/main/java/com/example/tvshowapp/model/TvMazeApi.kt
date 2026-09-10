package com.example.tvshowapp.model

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvMazeApi {

    @GET("shows")
    suspend fun getShows(
        @Query("page") page: Int = 0
    ): List<Show>

    @GET("shows/{id}")
    suspend fun getShow(
        @Path("id")id: Int
    ): Show
}